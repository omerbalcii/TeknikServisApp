import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import axiosconfig from '../utils/axiosconfig';
import { getAxiosHeaders } from '../utils/Utils';

const CartPage = () => {
  const { state } = useLocation();
  const selectedRows = state?.selectedRows || [];
  const [cardNumber, setCardNumber] = useState('');
  const [paymentInfo, setPaymentInfo] = useState([]); // Yeni eklenen state

  const calculateTotalPrice = () => {
    let totalPrice = 0;
    selectedRows.forEach((row) => {
      totalPrice += row.price;
    });
    return totalPrice;
  };

  const handleCheckout = () => {
    const updatedPaymentInfo = selectedRows.map((item) => ({
      sale_id: item.id,
      credit_card: cardNumber,
    }));

    // Yeni kart bilgilerini state'e kaydet
    setPaymentInfo(updatedPaymentInfo);
    axiosconfig
    .post("/buy/saveAll", updatedPaymentInfo, getAxiosHeaders())
    .then((response) => {
      if (response.status === 200) {
        alert("Siparişiniz tamamlandı!");
        setTimeout(() => {
          window.location.href = "/"; 
        }, 1000); 
      }
    })
    .catch((error) => {
      alert("Sipariş tamamlanamadı. Hata: " + error.message);
    });
    console.log('Ödeme yapıldı! Kart Bilgileri:', updatedPaymentInfo);
  };
  return (
    <div>
      <h1>Sepet</h1>
      <table className='table table-striped'>
        <thead>
          <tr>
            <th>ID</th>
            <th>Note</th>
            <th>Price</th>
            <th>Product Name</th>
          </tr>
        </thead>
        <tbody>
          {selectedRows.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.note}</td>
              <td>{item.price}</td>
              <td>{item.product}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div>
        <h6>Sepet toplamı : {calculateTotalPrice()} ₺</h6>
        <h5>Tamamlamak için kart bilgilerinizi giriniz</h5>
        <label>
          Kart Numarası:
          <input
            type="text"
            className='form form-control'
            value={cardNumber}
            onChange={(e) => setCardNumber(e.target.value)}
          />
        </label>
        <button type="button" className='btn btn-primary' onClick={handleCheckout}>
          Ödeme Yap
        </button>
      </div>
    </div>
  );
};

export default CartPage;
