import { useNavigate } from "react-router-dom";
import { UserContext } from "../context/UsernameContext";
import { useContext } from "react";
import axiosconfig from "../utils/axiosconfig";
import { ITokenInfo } from "../model/ITokenInfo";
import { Button, Stack } from "react-bootstrap";

export default function LoginComponent() {
  const mynavigate = useNavigate();

  const ourUserContext = useContext(UserContext);

  //Berke
  function parseTokenInfo(tokenInfoString: string): ITokenInfo | null {
    const tokenPattern = /token='(.*?)'/;
    const usernamePattern = /username='(.*?)'/;
    const authoritiesPattern = /authorities=\[(.*?)\]/; // Still capturing the content within brackets

    const tokenMatch = tokenInfoString.match(tokenPattern);
    const usernameMatch = tokenInfoString.match(usernamePattern);
    const authoritiesMatch = tokenInfoString.match(authoritiesPattern);

    if (!tokenMatch || !usernameMatch || !authoritiesMatch) {
      console.error("Failed to parse token info");
      return null;
    }

    return {
      token: tokenMatch[1],
      username: usernameMatch[1],
      authorities: authoritiesMatch[1], // Directly assigning the captured string
    };
  }
  //BERKE
  function handleSubmitUser() {
    axiosconfig
      .post("/login", { username: "user", password: "1234" })
      .then((response) => {
        const rawTokenInfo = response.data;
        const tokenInfo = parseTokenInfo(rawTokenInfo);

        if (tokenInfo) {
          localStorage.setItem("username", tokenInfo.username);
          localStorage.setItem("userjwt", tokenInfo.token);
          //console.log(localStorage.getItem("userjwt"))
          localStorage.setItem("authorities", tokenInfo.authorities);
          ourUserContext.setterforusername(tokenInfo.username);
          mynavigate("/");
        } else {
          console.error("Failed to parse token info");
          // Handle parsing failure appropriately
        }
      })
      .catch((error) => {
        console.error("Login error:", error);
        // Handle login error appropriately
      });
  }

  function handleSubmitAdmin() {
    axiosconfig
      .post("/login", { username: "admin", password: "1234" })
      .then((response) => {
        const rawTokenInfo = response.data;
        const tokenInfo = parseTokenInfo(rawTokenInfo);

        if (tokenInfo) {
          localStorage.setItem("username", tokenInfo.username);
          localStorage.setItem("userjwt", tokenInfo.token);
          //console.log(localStorage.getItem("userjwt"))
          localStorage.setItem("authorities", tokenInfo.authorities);
          ourUserContext.setterforusername(tokenInfo.username);
          console.log(response.data);
          mynavigate("/");
        } else {
          console.error("Failed to parse token info");
          // Handle parsing failure appropriately
        }
      })
      .catch((error) => {
        console.error("Login error:", error);
        // Handle login error appropriately
      });
  }
  /*  
  function handleSubmitUser() {
    axiosconfig
      .post("/login", { username: "user", password: "1234" })
      .then((response) => {
        const tokenInfo: ITokenInfo = response.data;
        const username = tokenInfo.username;
        const userjwt = tokenInfo.token;
        const authorities = tokenInfo.authorities;
        localStorage.setItem("username", username);
        localStorage.setItem("userjwt", userjwt);
        console.log(tokenInfo.token.toString());
        localStorage.setItem("authorities", authorities);
        // bu bütün sayfayı refresh eder context 'e gerek kalmaz, ama güzel bir çözüm değil
        // window.location.replace("/");
        ourUserContext.setterforusername(username);
        mynavigate("/");
      });
  }
*/
  return (
    <Stack gap={3}>
      <Button variant="outline-success" onClick={handleSubmitAdmin}>
        Admin Login
      </Button>
      <Button variant="outline-success" onClick={handleSubmitUser}>
        User Login
      </Button>
    </Stack>
  );
}
