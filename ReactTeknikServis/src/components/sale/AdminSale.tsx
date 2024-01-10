import React, { FormEvent, useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Button, Card, FormControl } from "react-bootstrap";
import { Form, useNavigate } from "react-router-dom";
import { ISale } from "../model/ISale";
import { getAxiosHeaders } from "../utils/Utils";
import { ISaleDTO } from "../model/ISaleDTO";

export default function AdminSale() {
  const [note, setNote] = useState("");
  const [price, setPrice] = useState<number>();
  const [product_id, setProductId] = useState<number>();
  const [tableData, setTableData] = useState<ISaleDTO[]>([]);
  const [selectedProductId, setSelectedProductId] = useState<number | null>(
    null
  );
  const [productOptions, setProductOptions] = useState<
    { id: number; name: string }[]
  >([]);

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!note || !price || !selectedProductId) {
      alert("Lütfen tüm alanları doldurun.");
      return;
    }
    const sendData = {
      note: note,
      price: price,
      product_id: selectedProductId,
    };
    axiosconfig
      .post("/sale/admin/save", sendData, getAxiosHeaders())
      .then((response) => {
        if (response.status == 200) {
          console.log(getAxiosHeaders());
          updateTableData();
          // navlink yerine router üzerinden navigate
          console.log(response);
        }
      });
  }
  const updateTableData = () => {
    axiosconfig
      .get("/sale/getalldto")
      .then((response) => {
        setTableData(response.data);
        console.log(response);
      })
      .catch((error) => {
        console.error("Error getting data:", error);
      });
  };
  const handleDelete = (idToDelete: number) => {
    axiosconfig
      .delete(`/sale/admin/deleteById/${idToDelete}`, getAxiosHeaders())
      .then((response) => {
        if (response.status === 200) {
          updateTableData();
          console.log(response);
        }
      })
      .catch((error) => {
        console.error("Error deleting data:", error);
      });
  };
  const fetchProductOptions = () => {
    axiosconfig
      .get("/product/getAllProduct")
      .then((response) => {
        console.log(response);
        setProductOptions(response.data);
      })
      .catch((error) => {
        console.error("Error getting product options:", error);
      });
  };
  useEffect(() => {
    axiosconfig.get("/sale/getAll").then((response) => {
      fetchProductOptions();
      updateTableData();
      console.log(response);
    });
  }, []);
  return (
    <div>
      <h1>Sales Table</h1>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Note</th>
            <th>Price</th>
            <th>Product Name</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {tableData.map((row) => (
            <tr key={row.id}>
              <td>{row.id}</td>
              <td>{row.note}</td>
              <td>{row.price}</td>
              <td>{row.product}</td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => handleDelete(row.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <h1>Post Data Form:</h1>
      <form onSubmit={handleSubmit} className="form">
        <div className="mb-3">
          <label htmlFor="note" className="form-label">
            Note:
          </label>
          <input
            type="text"
            className="form-control"
            id="note"
            value={note}
            onChange={(e) => setNote(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="price" className="form-label">
            Price:
          </label>
          <input
            type="text"
            className="form-control"
            id="price"
            value={price}
            onChange={(e) => setPrice(parseInt(e.target.value) || 0)}
          />
        </div>
        <div className="mb-3">
          <select
            className="form-control"
            id="product_id"
            value={selectedProductId || ""}
            onChange={(e) =>
              setSelectedProductId(parseInt(e.target.value) || null)
            }
          >
            <option value="" disabled>
              Select a product
            </option>
            {productOptions.map((product) => (
              <option key={product.id} value={product.id}>
                {product.name}
              </option>
            ))}
          </select>
        </div>
        <button type="submit" className="btn btn-primary">
          Submit
        </button>
      </form>
    </div>

    // <div>
    //   <h1>Post Data Form:</h1>
    //   <form onSubmit={handleSubmit}>
    //     <label>
    //       Note:
    //       <input
    //         type="text"
    //         value={note}
    //         onChange={(e) => setNote(e.target.value)}
    //       />
    //     </label>
    //     <br />
    //     <label>
    //       Price:
    //       <input
    //         type="text"
    //         value={price}
    //         onChange={(e) => setPrice(parseInt(e.target.value))}
    //       />
    //     </label>
    //     <br />
    //     <label>
    //       Product ID:
    //       <input
    //         type="text"
    //         value={product_id}
    //         onChange={(e) => setProductId(parseInt(e.target.value))}
    //       />
    //     </label>
    //     <br />
    //     <button type="submit">Submit</button>
    //   </form>
    // </div>
  );
}
