# 託運委託平台 Demo（Spring Boot + 前端 + 綠界金流）

> consign-platform-main  
> 後端：`consignwork`　|　前端：`frontend-consign`

這是一個練習「託運委託平台」的全端專案，模擬會員在平台上建立託運委託訂單，並透過 **綠界金流（ECPay）信用卡付款** 完成付款流程。  
專案同時整合：

- Spring Boot + Spring Data JPA
- MySQL 資料庫
- 綠界金流測試環境
- 前後端分離（簡易 HTML/CSS/JS 前端）
- ngrok 將本機服務暴露到外網，讓綠界可回呼（Callback）

---

## 功能介紹

### 1. 託運委託管理

- 建立託運委託單（Consign Order）
  - 起始地址（start_address）
  - 目的地址（end_address）
  - 預計取件時間（expected_pickup_time）
  - 貨物說明（goods_description）
  - 貨物重量（goods_weight）
  - 委託金額（total_price）
- 查詢自己的託運委託列表

### 2. 付款流程（綠界 ECPay）

- 根據託運委託單建立付款紀錄（Payment）
- 呼叫綠界金流 API，產生自動送出 form 的結帳 HTML
- 導向至綠界測試環境完成信用卡付款
-（已預留）綠界付款完成後，透過 Callback / Return URL 更新付款狀態與訂單狀態

---

## 系統架構

```text
consign-platform-main
├─ consignwork/          # Spring Boot 後端
│  ├─ src/main/java/demo/consign
│  │   ├─ controller/    # PaymentController, EcpayCallbackController, ConsignOrderController ...
│  │   ├─ entity/        # ConsignOrder, Payment, User ...
│  │   ├─ repository/    # ConsignOrderRepository, PaymentRepository, UserRepository ...
│  │   ├─ service/       # ConsignOrderService, PaymentService, EcpayService ...
│  │   └─ util/          # CheckMacValueUtil 等工具類別
│  └─ src/main/resources/
│      └─ application.yml  # DB 連線、server port、ECPay 相關設定
│
└─ frontend-consign/     # 前端（純靜態網頁）
    ├─ index.html        # 假登入 + 快速導向測試結帳
    ├─ consign-create.html  # 新增託運委託 + 立即付款
    ├─ consign-list.html    # 我的託運委託列表（呼叫後端 API）
    ├─ css/style.css
    └─ js/
        ├─ api.js        # 簡易 API wrapper (fetch)
        ├─ auth.js       # 假登入（localStorage 紀錄 userId）
        └─ consign.js    # 建立委託 + 呼叫後端建立付款並導向綠界

---

## 使用技術（Tech Stack）

### 後端

- Java 21
- Spring Boot（Spring Web / Spring MVC）
- Spring Data JPA + Hibernate
- MySQL 8
- Lombok（如有使用）

### 前端

- HTML5 / CSS3
- 原生 JavaScript（Fetch API）
- VS Code + Live Server（或其他靜態檔案伺服器）

### 其他

- Git / GitHub
- ngrok（將 `localhost:8080` 暴露到外網給綠界回呼）
- 綠界 ECPay 測試環境（信用卡測試）
