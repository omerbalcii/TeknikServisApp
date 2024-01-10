import { useState } from "react";
import { Button, Card, Form, FormControl, Stack } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import axiosconfig from "../utils/axiosconfig";
import { getAxiosHeaders } from "../utils/Utils";

export default function SignUpComponent() {
  // state olmasa da gayet normal olabilirdi
  // bu örneği state siz yapmaya çalışabilirsiniz, mesela useref veya form kütüphaneleri
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [email, setMail] = useState<string>("");

  const mynavigate = useNavigate();

  function handleSubmit() {
    // form sayfayı post ettirmesin diye
    // burası görünemse de bir json veya bir class
    // burada IOgretmen de kullanılabilir ama id 'ye ihtiyacım yok
    const sendData = {
      username: username,
      password: password,
      email:email,
    };
    axiosconfig.post("user/save", sendData, getAxiosHeaders()).then((res) => {
      if (res.status === 200) {
        // navlink yerine router üzerinden navigate
        // /ogretmen linkine tıklamış gibi
        mynavigate("/");
      }
    });
  }

  return (
    <Stack>
      <h1>User SignUp</h1>
      <Stack gap={3}>
    <Card>
      <Card.Body className="shadow">
      <br></br>
        <Form method="post" onSubmit={(e) => handleSubmit()}>
          <FormControl
            type="text"
            placeholder="İsim"
            onChange={(e) => {
              setUsername(e.target.value);
            }}
          ></FormControl>
          <br></br>
          <FormControl
            type="text"
            placeholder="Mail"
            onChange={(e) => {
              setMail(e.target.value);
            }}
          ></FormControl>
          <br></br>
          <FormControl
            type="text"
            placeholder="Password"
            onChange={(e) => {
              setPassword(e.target.value);
            }}
          ></FormControl>
          <br></br>
          <Button variant="outline-primary"  onClick={handleSubmit}>
            Ekle
          </Button>
        </Form>
      </Card.Body>
    </Card>
  </Stack>
  </Stack>
  );
}
