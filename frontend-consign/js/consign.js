document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("consignForm");
  const msg = document.getElementById("msg");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msg.textContent = "建立委託中...";

    try {
      const shipperId = getCurrentUserId();

      const payload = {
        shipperId: shipperId,
        startAddress: document.getElementById("startAddress").value.trim(),
        endAddress: document.getElementById("endAddress").value.trim(),
        expectedPickupTime: document.getElementById("pickupTime").value,
        goodsDescription: document.getElementById("goodsDesc").value.trim(),
        goodsWeight: parseFloat(document.getElementById("goodsWeight").value || "1"),
        totalPrice: parseInt(document.getElementById("totalPrice").value || "0", 10),
      };

      const order = await apiRequest("/api/consign/orders", {
        method: "POST",
        body: JSON.stringify(payload),
      });

      msg.textContent = `委託建立成功，ID = ${order.id || order.consignId}，準備導向綠界付款...`;

      const consignId = order.id || order.consignId;
      const payerId = shipperId;

      const html = await apiRequest(`/api/payment/ecpay/checkout/${consignId}?payerId=${payerId}`, {
        method: "POST",
      });

      const win = window.open("", "_blank");
      win.document.open();
      win.document.write(html);
      win.document.close();
    } catch (err) {
      console.error(err);
      msg.textContent = "建立委託或產生付款頁失敗：" + err.message;
    }
  });
});
