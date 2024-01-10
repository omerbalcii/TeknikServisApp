import { EffectCallback,useEffect, useState } from "react";
import axiosconfig from "../utils/axiosconfig";
import { Row, Spinner, Table } from "react-bootstrap";
import { IBooking } from "../model/IBooking";
import { getAxiosHeaders } from "../utils/Utils";

export default function BookingAdminComponent() {
  const [bookings, setBookings] = useState<IBooking[]>([]);
  useEffect(initialize(), []);

  function initialize(): EffectCallback {
    return () => {
      axiosconfig
        .get("/booking/admin/sortascbooking", getAxiosHeaders())
        .then((response) => {
          setBookings(response.data);
        });
    };
  }

  function completeBooking(id: any) {
    return () => {
      axiosconfig //put yaparken parametreyi URL,DATA,GETAXIOS olarak yap
        .put("/booking/admin/bookingcomplete/" + id, "",getAxiosHeaders())
        .then(initialize());
    };
  }

  function processBooking(id: any) {
    return () => {
      axiosconfig
        .put("/booking/admin/bookingprocess/" + id,"", getAxiosHeaders())
        .then(initialize());
    };
  }

  return (
    <div>
      {bookings.length === 0 && (
        <Row className="justify-context-center">
          <Spinner animation="border" variant="primary"></Spinner>
        </Row>
      )}

      {bookings.length !== 0 && (
        <Table responsive striped bordered>
          <thead className="table-dark">
            <tr>
              <th className="col-auto">ID</th>
              <th className="col-auto">USERNAME</th>
              <th className="col-auto">DATE</th>
              <th className="col-auto">NOTE</th>
              <th className="col-auto">STATUS</th>
              <th className="col-auto">PROCESS</th>
              <th className="col-auto">COMPLETE</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking, index) => (
              <tr key={index}>
                <td className="col-auto">{booking.id}</td>
                <td className="col-auto">{"user"}</td>
                <td className="col-auto">
                  {new Date(booking.booking_date).toLocaleString()}
                </td>
                <td className="col-auto">{booking.note}</td>
                <td className="col-auto">{booking.status}</td>

                <td className="col-auto">
                  <button
                    className="btn btn-outline-danger"
                    onClick={processBooking(booking.id)}
                  >
                    PROCESSING
                  </button>
                </td>
                <td className="col-auto">
                  <button
                    className="btn btn-outline-success"
                    onClick={completeBooking(booking.id)}
                  >
                    COMPLETING
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </div>
  );
}
