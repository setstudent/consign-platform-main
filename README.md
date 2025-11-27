# 託運委託平台（綠界金流測試版）

這是一個示範專案，用來練習：

- Spring Boot 後端 + MySQL + JPA
- 託運委託單 / 付款紀錄的基本資料表
- 串接綠界全方位金流（測試環境 MerchantID=2000132）
- 前端簡單 HTML 頁面，示範「建立測試委託單並導到綠界付款頁」

> 注意：
> - 專案目前只使用「綠界測試環境」，不會真的扣款。
> - 不需要先申請自己的特店，只要把 MySQL 設定好就能跑完整流程。
> - 安全性 / 權限驗證（JWT 等）為了簡化暫時省略。

## 專案結構

```text
consign-platform-main/
├─ README.md
├─ consignwork/            # Spring Boot 後端
└─ frontend-html/          # 靜態前端示範頁面
```

### 後端啟動方式

1. 建立 MySQL 資料庫：

   ```sql
   CREATE DATABASE consign_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. 修改 `consignwork/src/main/resources/application.yml` 裡的：

   - `spring.datasource.username`
   - `spring.datasource.password`

3. 在 `consignwork` 目錄下執行：

   ```bash
   mvn spring-boot:run
   ```

4. 啟動後，打開瀏覽器到：

   - `http://localhost:8080/demo/test-checkout`  
     → 會建立一筆測試委託單與付款，並自動導到綠界「測試付款頁」。

### 主要 API

- `POST /api/consign/orders` 建立委託單（JSON）
- `POST /api/payment/ecpay/checkout/{consignId}?payerId=1` 建立綠界訂單，回傳自動 submit 的 HTML form
- `POST /api/payment/ecpay/return` 接收綠界背景通知（ReturnURL），驗證 CheckMacValue 後更新付款/委託單狀態
