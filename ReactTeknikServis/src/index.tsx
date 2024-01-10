import React from "react";
import ReactDOM from "react-dom/client";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Col, Container, Row } from "react-bootstrap";
import MenuComponent from "./components/menu/MenuComponent";
import IndexComponent from "./components/IndexComponent";
import { UserContextProvider } from "./components/context/UsernameContext";
import LoginComponent from "./components/Login/LoginComponent";
import SignUpComponent from "./components/Login/SignUpComponent";
import TakeBookingComponent from "./components/booking/TakeBookingComponent";
import BookingAdminComponent from "./components/booking/BookingAdminComponent";
import AdminSale from "./components/sale/AdminSale";
import CustomerSale from "./components/sale/CustomerSale";
import CartPage from "./components/sale/CartPage";
import ProposalAdminComponent from "./components/proposals/ProposalAdminComponent";
import ProposalUserComponent from "./components/proposals/ProposalUserComponent";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <UserContextProvider>
      <BrowserRouter>
        <Container className="pt-3" fluid>
          <Row>
            <Col xs="2">
              <MenuComponent />
            </Col>
            <Col xs="10">
              <Routes>
                {/* {path lere dikkat} */}
                <Route path="/" element={<IndexComponent />} />
                <Route
                  path="/bookingAdmin"
                  element={<BookingAdminComponent />}
                />
                <Route path="/login" element={<LoginComponent />} />
                <Route path="user/save" element={<SignUpComponent />} />
                <Route path="/sale/save" element={<AdminSale />} />
                <Route path="/sale/getsales" element={<CustomerSale />} />
                <Route path="/cart" element={<CartPage />} />
                <Route path="/booking" element={<TakeBookingComponent />} />
                <Route path="/proposal" element={<ProposalUserComponent/>} />
                <Route path="/proposalAdmin" element={<ProposalAdminComponent/>} />
              </Routes>
            </Col>
          </Row>
        </Container>
      </BrowserRouter>
    </UserContextProvider>
  </React.StrictMode>
);
