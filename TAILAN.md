# F.CSM311 Лаборатори 12 Тайлан

## Ерөнхий мэдээлэл

- **Хичээл:** F.CSM311 Программ хангамжийн бүтээлт
- **Лаборатори:** №12 — Designing for Robustness
- **Оюутны нэр:** Ariunbold Uuganbayr
- **Хөгжүүлэлтийн орчин:** Java 17+, Maven 3.6+, JUnit 5

## Ажлын зорилго

Энэхүү лабораторийн ажлаар бат бөх (robust) програм зохиох үндсэн аргуудыг Java код дээр хэрэгжүүлэв. Үүнд fail-fast шалгалт, Optional/Null Object, exception translation, retry backoff+jitter, circuit breaker паттернүүдийг туршиж, unit тестээр баталгаажуулсан.

## Хийсэн ажлууд

### Даалгавар 1 — Fail-fast ба Preconditions (`task1/BankAccount`)

- `owner` null/blank үед `IllegalArgumentException` шиддэг болгосон.
- `initialBalance < 0` үед fail-fast шалгалт хийсэн.
- `withdraw` дээр:
  - `amount <= 0` шалгалт нэмсэн.
  - үлдэгдэл хүрэлцэхгүй бол `InsufficientFundsException` шиддэг болгосон.
  - `assert balance >= 0` invariant нэмсэн.
- `transfer` дээр:
  - `from`/`to` null шалгалт,
  - ижил account руу шилжүүлэхийг хориглосон,
  - `withdraw` амжилттай болсны дараа `deposit` хийх дараалалтай болгосон.
- `deposit` дээр `amount <= 0` шалгалт нэмсэн.

### Даалгавар 2 — Optional ба Null Object Pattern (`task2`)

- `UserRepository.findByEmail`-ийн буцах төрлийг `Optional<User>` болгосон.
- `Optional.ofNullable(...)` ашиглан null буцаалтыг арилгасан.
- `NullLogger`-ийг no-op байдлаар хэрэгжүүлсэн:
  - `log(...)` юу ч хийхгүй,
  - `logCount()` үргэлж `0` буцаана.
- `GreetingService.greet(...)`-ийг Optional API-р:
  - `.map(...)` + `.orElse(...)` ашиглан хэрэгжүүлсэн.

### Даалгавар 3 — Exception Handling (`task3/FileConfigLoader`)

- `path == null` үед `IllegalArgumentException` шиддэг fail-fast нэмсэн.
- `BufferedReader`-ийг `try-with-resources` ашиглан нээдэг болгосон.
- `NoSuchFileException`-ыг:
  - `"config file not found: <path>"` мессежтэй
  - `ConfigLoadException` болгон орчуулсан.
- Бусад `IOException`-ыг:
  - `"failed to read config: <path>"` мессежтэй
  - `ConfigLoadException` болгон wrap & rethrow хийсэн.
- `cause` мэдээллийг хадгалсан.

### Даалгавар 4 — Retry Executor (`task4/RetryExecutor`)

- `execute(Supplier<T> op)` алгоритмыг бүрэн хэрэгжүүлсэн.
- `NonRetryableException` гарвал шууд дамжуулдаг болгосон.
- `RuntimeException` дээр:
  - сүүлийн алдааг хадгалж,
  - `maxAttempts` хүрээгүй бол exponential backoff + jitter тооцоолон `sleep` хийдэг болгосон.
- Сүүлийн оролдлогын дараа унтдаггүй логик баримталсан.
- `InterruptedException` үед thread interrupt төлөвийг сэргээж (`Thread.currentThread().interrupt()`), runtime алдаа болгон дамжуулсан.

### Даалгавар 5 — Circuit Breaker (`task5/SimpleCircuitBreaker`)

- `execute(...)` дотор:
  - төлөв `OPEN` бол `CircuitBreakerOpenException` шиддэг болгосон.
  - амжилттай дуудлагад `onSuccess()`,
  - алдаатай дуудлагад `onFailure()` ажиллуулдаг болгосон.
- `onSuccess()`:
  - төлөвийг `CLOSED`,
  - `failureCount = 0` болгож reset хийсэн.
- `onFailure()`:
  - `HALF_OPEN` дээр алдаа гарвал `OPEN` руу буцааж, `openedAt` шинэчилж, тоолуурыг reset хийсэн.
  - `CLOSED` дээр алдаа бүрт `failureCount++`, threshold хүрвэл `OPEN` болгосон.

## Туршилтын үр дүн

Дараах командыг ажиллуулж баталгаажуулсан:

```bash
mvn test
```

Үр дүн:

- **Tests run: 30**
- **Failures: 0**
- **Errors: 0**
- **Skipped: 0**
- **BUILD SUCCESS**

## Дүгнэлт

Лаборатори 12-ын бүх шаардлагатай TODO хэсгүүдийг гүйцэт бөглөж, fail-fast, optional/null safety, exception translation, retry, circuit breaker-ийн загваруудыг тестээр баталгаажуулан амжилттай дуусгав.
