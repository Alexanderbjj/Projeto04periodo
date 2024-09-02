class AuthService {

 auths = [{
  path: '/', roles: ['ROLE_ADMIN', 'ROLE_USER']
 }, {
  path: '/sobre', roles: ['ROLE_USER']
 }, {
  path: '/projetos', roles: ['ROLE_PROJETOS']
 }, {
  path: '/usuarios', roles: ['ROLE_ADMIN']
 }]

 dadosAutenticado = () => {
  let dados = sessionStorage.getItem("dados-usuario");
  dados = dados != null ? JSON.parse(dados) : null;
  return dados;
 }

 validaPermissao = (path) => {
  let ret = false;
  let pathVerifica = false;
  this.dados = this.dadosAutenticado();
  if (this.dados != null) {
   for (let i = 0; i < this.auths.length; i++) {
    let e = this.auths[i];
    if (e.path == path) {
     pathVerifica = true;
     for (let j = 0; j < this.dados.permissoes.length; j++) {
      let p = this.dados.permissoes[j];
      let idx = e.roles.indexOf(p.authority);
      if (idx >= 0) {
       ret = true;
      }
     }
    }
   }
  }
  if (!pathVerifica) {
   ret = true;
  }
  return ret;
 }
}
export default new AuthService();
