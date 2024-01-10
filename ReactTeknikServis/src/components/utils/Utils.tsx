import { AxiosRequestConfig } from "axios";

export function getAxiosHeaders(): AxiosRequestConfig {

  console.log(localStorage.getItem("userjwt"))
  return {
    headers: localStorage.getItem("userjwt") !== null ? { Authorization: "Bearer " + localStorage.getItem("userjwt"), "Content-Type": "application/json" } : { "Content-Type": "application/json" },
  };
}
