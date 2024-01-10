import {
  Button,
  Card,
  FormControl,
  Form,
  FormSelect,
  Table,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { getAxiosHeaders } from "../utils/Utils";
import { IService } from "../model/IService";
import axiosconfig from "../utils/axiosconfig";
import { IBooking } from "../model/IBooking";
import { useEffect, useState } from "react";

export default function TakeBookingComponent() {
  console.log(localStorage.getItem("userjwt"));

  const [note, setNote] = useState("");
  const [serviceId, setServiceId] = useState(-1);
  const [services, setServices] = useState<IService[]>([]);
  const [myBookings, setMyBookings] = useState<IBooking[]>([]);

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // getMonth() returns 0-11
    const day = date.getDate();

    // Format: YYYY-MM-DD
    return `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(
      2,
      "0"
    )}`;
  };

  const navigate = useNavigate();

  useEffect(() => {
    // iki sitek asenkron çalışıyor ve paralel sonuç bekliyor
    //burada bütün servisleri get yaptır
    axiosconfig.get("/services/getAll").then((response) => {
      try {
        setServices(response.data);
      } catch (err) {}
    });
    //burası user id ile aynı olmakı
    axiosconfig
      .get("/booking/user/getAll", getAxiosHeaders())
      .then((response) => {
        try {
          setMyBookings(response.data);
        } catch (err) {}
      });
  }, []);

  function handleSubmit(event) {
    const token = localStorage.getItem("userjwt");
    event.preventDefault();

    const sendData = {
      note: note,
      service_id: serviceId,
    };

    axiosconfig
      .post("booking/user/save", sendData, getAxiosHeaders())
      .then((res) => {
        console.log(getAxiosHeaders);
        if (res.status === 200) {
          navigate("/booking");
        }
      })
      .catch((error) => {
        console.error("There was an error!", error);
      });
  }

  return (
    <Card>
      <Card.Body className="shadow">
        <Form method="post" onSubmit={handleSubmit}>
          <FormControl
            type="text"
            placeholder="Customer Note"
            onChange={(e) => setNote(e.target.value)}
          />

          <FormSelect
            onChange={(e) => setServiceId(Number.parseInt(e.target.value))}
          >
            <option key={-1} value={-1}>
              Servis seçiniz
            </option>
            {services.map((service, i) => (
              <option key={i} value={service.id}>
                {service.description}
              </option>
            ))}
          </FormSelect>
          <br />
          <Button variant="outline-primary" type="submit">
            Booking Submit
          </Button>
        </Form>
      </Card.Body>
      <hr />
      <center>
        <h1 style={{ fontWeight: "bold" }}>My Booking List</h1>
      </center>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Service</th>
            <th>Note</th>
            <th>Date</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {myBookings.map((myBooking, i) => (
            <tr key={i}>
              <td>{myBooking.id}</td>
              <td>{services.at(myBooking.service_id - 1)?.description}</td>
              <td>{myBooking.note}</td>
              <td>{formatDate(myBooking.booking_date)}</td>
              <td>{myBooking.status}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Card>
  );
}
