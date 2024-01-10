import React, { FormEvent, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { getAxiosHeaders } from "../utils/Utils";
import { ISale } from "../model/ISale";
import { ISaleDTO } from "../model/ISaleDTO";
import { useNavigate } from "react-router-dom";

export default function CustomerSale() {
  const [note, setNote] = useState("");
  const [price, setPrice] = useState<number>();
  const [product_id, setProductId] = useState<number>();
  const [tableData, setTableData] = useState<ISaleDTO[]>([]);
  const [selectedProductId, setSelectedProductId] = useState<number | null>(
    null
  );
  const [selectedRows, setSelectedRows] = useState<ISaleDTO[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const navigate = useNavigate();

  const handleAddToCart = () => {
    if (selectedRows.length === 0) {
      alert("Lütfen en az bir ürün seçiniz.");
    } else {
      navigate("/cart", { state: { selectedRows } });
    }
  };

  const handleCheckboxChange = (row: ISaleDTO) => {
    if (selectedRows.some((selectedRow) => selectedRow.id === row.id)) {
      setSelectedRows(
        selectedRows.filter((selectedRow) => selectedRow.id !== row.id)
      );
    } else {
      setSelectedRows([...selectedRows, row]);
    }
  };

  const updateTableData = () => {
    axiosconfig
      .get("/sale/getalldto")
      .then((response) => {
        setTableData(response.data);
      })
      .catch((error) => {
        console.error("Error getting data:", error);
      });
  };

  useEffect(() => {
    axiosconfig.get("/sale/getAll").then((response) => {
      updateTableData();
    });
  }, []);

  const filteredTableData = tableData.filter((row) =>
    row.product.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div>
      <h1>Sales Table</h1>
      <div>
        <label>
          Arama:
          <input
            className="form form-input"
            type="text"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </label>
      </div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Seç</th>
            <th>ID</th>
            <th>Note</th>
            <th>Price</th>
            <th>Product Name</th>
          </tr>
        </thead>
        <tbody>
          {filteredTableData.map((row) => (
            <tr key={row.id}>
              <td>
                <input
                  type="checkbox"
                  checked={selectedRows.some(
                    (selectedRow) => selectedRow.id === row.id
                  )}
                  onChange={() => handleCheckboxChange(row)}
                />
              </td>
              <td>{row.id}</td>
              <td>{row.note}</td>
              <td>{row.price}</td>
              <td>{row.product}</td>
              <td></td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="btn btn-primary" onClick={handleAddToCart}>
        Sepete Ekle
      </button>
    </div>
  );
}
