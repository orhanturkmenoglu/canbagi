## 1. PROBLEMİ DOĞRU TANIMLAMA

### CAN BAĞI

**Donor ile hastaneyi doğru kan eşleşmesi ile hızlı ve güvenilir bir şekilde eşleştirmek**

---

## 0.1 – FUNCTIONAL REQUIREMENTS (Sistem ne yapmalı?)

### Ana Fonksiyonlar

1. **Donor Kaydı**

    * Kan grubu
    * Lokasyon
    * Uygunluk durumu (aktif / pasif)
    * Son bağış tarihi

2. **Hastane Talebi**

    * İstenen kan grubu
    * Aciliyet seviyesi
    * Lokasyon
    * Gerekli ünite sayısı

3. **Eşleşme (Matching)**

    * Kan grubu uyumu
    * Mesafe (geo-based)
    * Uygunluk
    * Öncelik (acil vs normal)

4. **Bildirim**

    * Donora anlık bildirim
    * Hastaneye eşleşme bilgisi

5. **Eşleşme Durumu**

    * Beklemede
    * Kabul edildi
    * Reddedildi
    * Timeout

> **Not:** Eşleşme stateful bir süreçtir.

---

## 0.2 – NON-FUNCTIONAL REQUIREMENTS (Sistem nasıl çalışmalı?)

### Performans

* Eşleşme süresi < **2 saniye**
* Bildirim gecikmesi < **1 saniye**

### Ölçeklenebilirlik

* 100 kullanıcı → çalışır
* 100.000 kullanıcı → çökmemeli

> **Refleks:** Bu sistem yatayda büyüyebilir mi?

### Availability

* Sistem %99.9 ayakta
* Bir servis ölürse sistem tamamen durmamalı

### Reliability

* Aynı hastane isteği iki kez işlenmemeli
* Donora aynı bildirim spam atılmamalı

### Güvenlik

* Donor bilgileri KVKK kapsamında
* Yetkisiz erişim engellenmeli

---

## 0.3 – Kısıtlar (Constraints)

* Donor bekleyemez
* Hastane retry atamaz
* Network her zaman stabil değil
* Bildirim bazen gitmez

> **Altın soru:** Bu sistem eventual consistency kabul eder mi?

---

## 0.4 – Failure Scenarios (Koda gelmeden düşünülmeli)

* Eşleşme servisi çökerse?
* Bildirim gönderilmezse?
* Aynı anda 2 hastane aynı donoru isterse?
* Donor kabul etmezse?

> **Not:** Architect burada doğar.

---

## 0.5 – System Boundary (En kritik)

### Sistem ne değildir?

* Kan bankası yönetim sistemi değildir
* Tıbbi karar sistemi değildir
* Manuel süreçleri yönetmez

### Sistem nedir?

* Hızlı eşleştirme ve haberleşme sistemi

---

# FAZ 1 – Değerlendirme

## 1️⃣ Bu sistemin ana amacı nedir?

**Hastanelerin acil kan taleplerini, uygun donorlerle düşük gecikme ve yüksek güvenilirlik sağlayarak gerçek zamanlı (real-time) olarak eşleştirmek.**

## 2️⃣ En kritik non-functional requirement hangisi?

**Latency (düşük gecikme)**

* Kan talebi ile donor eşleşmesi arasındaki gecikme sistemin iş değerini doğrudan belirler
* Sistem senkron ve uzun süreli bloklayıcı işlemlerden kaçınacak şekilde tasarlanmalıdır

**Latency kritikse otomatik düşünülmesi gerekenler:**

* Senkron HTTP nerede kırılır?
* Async / event-driven zorunlu mu?
* DB lock’ları kabul edilebilir mi?
* Retry kaç ms sonra olmalı?

## 3️⃣ En korkulan failure senaryosu

* Aynı anda çok sayıda hastaneden gelen eş zamanlı kan taleplerinin sistem kapasitesini aşması
* Taleplerin senkron işlenmesi sonucu eşleşme gecikmeleri ve sistemin tamamen bloke olması

---

# FAZ 2 – Asenkron Mimari ve Queue

## 2.1 – Senkron vs Asenkron Kararı

**Soru:** Hastane kan talebi oluşturduğunda HTTP request ne zaman dönmeli?

* Eşleşme bitince mi?
* Yoksa “talep alındı” deyip mi?

**Cevap:**

* Kan talebi oluşturma işlemi belirsiz süreli ve dış bağımlılıkları olan bir süreçtir
* HTTP request’in eşleşme tamamlanana kadar açık kalması ölçeklenebilirliği ve güvenilirliği tehdit eder

**Senkron olsaydı:**

* 1000 hastane = 1000 açık thread
* Thread pool dolar
* Timeout başlar
* Sistem kilitlenir

**Async olunca:**

* Request hemen döner
* Matching arkada çalışır
* Sistem yük altında da ayakta kalır

---

## 2.2 – Async Nasıl Yapılacak?

**Hastane talebi alındıktan sonra bilgi nereye gider?**

1. Direkt DB’ye yazılır, sonra worker okur
2. Message Queue’ya event olarak atılır
3. Başka bir servise sync çağrı yapılır

**Cevap:**

* Hastane talepleri doğrudan DB’ye yazılmak yerine message queue üzerinden asenkron işlenmelidir
* Ani trafik artışlarında DB korunur
* Sistem yatay ölçeklenebilir olur
* Talepler kontrollü şekilde tüketilir

**Bu kararın çözdükleri:**

1. DB Protection (Backpressure)
2. Load Smoothing (Producer hızlı, consumer kontrollü)
3. Failure Isolation (Consumer ölür → mesaj durur ama kaybolmaz)

---

## 2.3 – Queue Semantics & Idempotency

**Soru:** Aynı kan talebi event’i iki kez tüketilirse ne olur?

**Cevap:**

* Sistem aynı event’in birden fazla kez işlenebileceği varsayımıyla tasarlanmalıdır
* İşi tekrar yaratmayan idempotent mekanizmalar olmalıdır

> **Doğru refleks:** İki kez tüketilse bile sonuç değişmemeli

### Idempotency Nasıl Sağlanır?

1. Unique Event ID + DB Constraint
2. Idempotency Key + Cache / DB
3. State Machine (status check)

**Ana çözüm:** 1️⃣ Unique Event ID + DB Constraint

* Hayati işlemler
* Kalıcı kayıt ihtiyacı
* Event işlendi mi sorusunun cevabı kalıcı olmalı

> Queue, cache, memory geçicidir; DB gerçektir

**Kavramsal Akış:**

* Her kan talebi için eventId üretilir (UUID)
* Consumer DB’ye eventId ile kayıt atar
* UNIQUE(event_id) constraint duplicate’i engeller

**Sonuç:** Event iki kez gelse bile sistem aynı sonucu üretir

---

## 2.4 – ACK Zamanı

**ACK (Acknowledgement):** “Bu mesajı aldım ve güvenle işledim.”

* ACK verilirse → mesaj queue’dan silinir
* ACK verilmezse → mesaj tekrar gönderilir

**Seçenekler:**

1. Mesaj alındığında (tehlikeli)
2. Matching tamamlandığında
3. DB’ye idempotent kayıt atıldıktan sonra ✅

**Doğru cevap:** 3️⃣ DB’ye idempotent kayıt atıldıktan sonra

* Mesaj geldi
* DB’ye eventId ile kayıt atıldı
* DB “OK” dedi (ya da zaten var)
* Mesaj güvenli
* ACK verilir

> Consumer ölse bile mesaj tekrar gelse bile DB duplicate’i engeller

---

# FAZ 3 – Data & Consistency

## 3.1 – Matching State (State Machine)

**Problem:** Matching tek adımlı değildir

* Talep geldi
* Donor bulundu
* Bildirim gitti
* Donor cevap verdi
* Hastane onayladı

**Çözüm:** State Machine

**Minimum state’ler:**

* REQUEST_RECEIVED
* MATCHING_IN_PROGRESS
* DONOR_NOTIFIED
* DONOR_ACCEPTED
* MATCH_CONFIRMED
* CANCELLED
* TIMEOUT

> Her adım observable, retry güvenli, crash sonrası devam edilebilir

---

## 3.2 – Consistency Kararı

**Soru:** Strong consistency mi, eventual consistency mi?

**Cevap:** Hibrit consistency

### Strong Consistency (Hata affetmez)

* Aynı donorun iki hastaneye verilmesi asla olamaz
* Donor availability tek kaynak
* DB constraint, transaction, row-level lock zorunlu

> “Donor allocation işlemi strong consistency gerektirir.”

### Eventual Consistency (Gecikme tolere edilir)

* Bildirimler (push, email)
* Monitoring / analytics

**Altın kural:**

> Kaynağın sahibi olan yerde strong consistency, bilginin kopyası olan yerde eventual consistency

---

## 3.3 – Transaction Gerçeği

**Soru:** Distributed transaction (2PC) yapılır mı?

**Cevap:** Hayır

**Neden yapılmaz?**

* Yavaş
* Kırılgan
* Scale etmez
* Debug edilemez

**Doğru yol:** Saga Pattern

**Saga:**

* Her adım kendi transaction’ını yapar
* Hata olursa compensating action çalışır

**Örnek:**

1. Donor reserve edildi
2. Notification gönderilemedi
3. Compensating action: donor release edilir

---

## 3.4 – Eşleşme Çökerse Ne Olur?

**Senaryo:**

* Matching service crash
* DB ayakta
* Queue dolu

**Sonuç:**

* Event queue’da bekler
* Consumer ayağa kalkınca devam eder
* State machine kaldığı yerden yürür

> Bu, production-ready sistemdir.
