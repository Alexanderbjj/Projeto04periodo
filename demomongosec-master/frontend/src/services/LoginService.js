class LoginService {
 dadosAutenticado = () => {
  let dados = sessionStorage.getItem("dados-usuario");
  dados = dados != null ? JSON.parse(dados) : null;
  return dados;
 }

 estaAutenticado = () => {
  return this.dadosAutenticado() != null;
 }

 deslogar = () => {
  sessionStorage.removeItem("dados-usuario");
 }

 logar = (_login, _senha) => {
  const data = new URLSearchParams();
  data.append('login', _login);
  data.append('senha', _senha);
  fetch("http://localhost:8080/auth/login", {
   method: 'POST',
   body: data
  }).then(r => {
   console.log(r);
   if (r.status != 200) {
    return Promise.reject(r);
   }
   return r.json();
  }).then(j => {
   console.log(j);
   sessionStorage.setItem("dados-usuario", JSON.stringify(j));
   if (this.router != null) {
    this.router.push("/");
   }
  }).catch(e => {
   e.json().then((j) => {
    console.log(e.status);
    console.log(j);
   })
  })
 }
}

export default new LoginService();