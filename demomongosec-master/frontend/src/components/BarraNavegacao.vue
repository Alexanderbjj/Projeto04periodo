<template>
  <div class="container-fluid mb-3 p-3 bg">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <a href="/">
        <img src="@/assets/logo.png" alt="Logo" class="navbar-brand" />
      </a>

      <!-- Botão mobile perguntar a romulo animação de botão-->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>

        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <router-link to="/" class="nav-link">Início</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/projetos" class="nav-link">Projetos</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/usuarios" class="nav-link">Usuários</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/sobre" class="nav-link">Sobre</router-link>
          </li>
        </ul>

      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <router-link to="/" class="nav-link">Início</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/projetos" class="nav-link">Projetos</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/usuarios" class="nav-link">Usuários</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/sobre" class="nav-link">Sobre</router-link>
          </li>
        </ul>
        <div class="deslogar">
          <div class="log-user">{{ logado.login }}</div> | <button class="bot-deslogar" @click="desloga">Sair</button>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import LoginService from "@/services/LoginService";
import AuthService from "@/services/AuthService";

export default {
  methods: {
    desloga() {
      LoginService.deslogar();
      this.$router.push(`/?d=${new Date()}`);
    },
  },
  computed: {
    logado() {
      return AuthService.dados;
    },
  },
};
</script>

<style scoped>
button {
  margin: 0 10px; /* Ajuste a margem conforme necessário */
  box-shadow: 1px 2px 1px 1px rgba(0, 0, 0, 0.745) !important;
}

.navbar {
  border-radius: 10px;
  font-size: 18px;
}

.navbar-brand {
  width: 130px;
  height: 40px;
  margin-right: 50px;
  margin-left: 20px;
}

.nav-item {
  margin-right: 10px; /* Espaçamento entre os itens do menu */
}

.deslogar {
  margin-left: auto;
  display: flex;
  align-items: center;
  margin-right: 20px;
}

.bot-deslogar {
  border-radius: 6px !important;
}

.bot-deslogar:hover {
  border-radius: 6px !important;
  background-color: rgb(254, 229, 2) !important;
}

.log-user {
  font-weight: bold;
  font-family: Helvetica;
  text-transform: uppercase;
  font-size: 19px;
  color: rgb(17, 0, 255);
}

/* Estilos responsivos */
@media (max-width: 768px) {
  .navbar-brand {
    margin-right: 20px; /* Ajuste do espaçamento da logo em telas pequenas */
    margin-left: 10px; /* Ajuste do espaçamento da logo em telas pequenas */
  }

  .navbar-nav {
    flex-direction: column; /* Menu em coluna em telas pequenas */
  }
}
</style>