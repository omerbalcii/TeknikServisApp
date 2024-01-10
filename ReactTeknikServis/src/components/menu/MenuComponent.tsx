import { Button, Stack } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { UserContext } from "../context/UsernameContext";
import { useContext } from "react";

export default function MenuComponent() {
  const mynavigate = useNavigate();
  const ourUserContext = useContext(UserContext);

  const menuStyle = {
    display: 'flex',
    height:'102%'
  }

  const menuTextStyle = {
    textDecoration: 'none',
    color: '#006600',
    marginTop:'10px',
  }

  const navButtonStyle = {
    textDecoration: 'none',
    color: '#006600',
    widht: '100px',
  }

  return (
      <Stack style={menuStyle}>
        <img src="a.png" style={{ width: "100px", marginLeft: "50px" }} />
        <h2 style={{ color: '#006600', marginTop: '20px' }}>Teknik Servis</h2>
        {/* react-router-dom 'dan gelen navlik olacak */}
        <NavLink style={menuTextStyle} to="/"> Home </NavLink>
        <NavLink style={menuTextStyle}
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          to="/booking"
          end
        >
          Bookings
        </NavLink>
        <NavLink style={menuTextStyle}
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          to="/bookingAdmin"
          end
        >
          Bookings (Admin)
        </NavLink>
        {/* context içerisindeki state bu componentin render edilmesini sağlıyor */}
        <NavLink style={menuTextStyle}
          to="/proposal"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          end
        >
          Proposal
        </NavLink>
        <NavLink style={menuTextStyle}
          to="/proposalAdmin"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          end
        >
          Proposal (Admin)
        </NavLink>
        <NavLink style={menuTextStyle}
          to="/sale/save"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Admin Sales
        </NavLink>
        <NavLink style={menuTextStyle}
          to="/sale/getsales"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Customer Sales
        </NavLink>
        
        <NavLink style={menuTextStyle}
          to="/konu"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
          end
        >
          Services
        </NavLink>

        <NavLink style={menuTextStyle}
          to="/konu/kaydet"
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Products
        </NavLink>
        <NavLink style={menuTextStyle}
          to="/ders"
          end
          className={({ isActive }) => (isActive ? "bg-info" : "")}
        >
          Sale_Log
        </NavLink>
        <br></br>
        <div>
          {localStorage.getItem("userjwt") === null && (
            <Button variant="outline-success" style={{ marginTop: "5px" }}>
              <NavLink style={navButtonStyle}
                to="user/save"
                className={({ isActive }) => (isActive ? "bg-info" : "")}
              >
                Join Us!
              </NavLink>
            </Button>
          )}
          <br></br>
          {localStorage.getItem("userjwt") === null && (
            <Button variant="outline-success" style={{ marginTop: "10px" }}>
              <NavLink style={navButtonStyle}
                to="/login"
                className={({ isActive }) => (isActive ? "bg-info" : "")}
              >
                Login
              </NavLink>
            </Button>
          )}
          {localStorage.getItem("userjwt") !== null && (
            <Button
              variant="outline-danger"
              onClick={() => {
                localStorage.removeItem("username");
                localStorage.removeItem("userjwt");
                localStorage.removeItem("authorities");
                ourUserContext.setterforusername("");
                // replace çalışmadı
                mynavigate("/", { replace: true });
              }}
            >
              Logout
            </Button>
          )}
        </div>
      </Stack>
  );
}
