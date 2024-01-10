import { createContext, useState } from "react";

// context te tutulacak veri yapısı, default değerler DEĞİL !!!!
export const UserContext = createContext({
  usernameeee: "",
  setterforusername: (somestring: string) => {},
});

// context e dışardan erişim sağlayacak provider
export const UserContextProvider = ({ children }) => {
  const [username, setUsername] = useState<string>(
    localStorage.getItem("username") as string
  );
  // bu provider dışarıdan çağırıldığında arka planda setstate yapar
  // dafult değerler localstorage dan gelen değer ve state setter fonksiyonudur
  return (
    <UserContext.Provider
      value={{
        usernameeee: username,
        setterforusername: setUsername,
      }}
    >
      {/* içerisine component yerleştirilebilsin diye */}
      {children}
    </UserContext.Provider>
  );
};
