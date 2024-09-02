<template>
    <!-- Template Vue.js -->
    <p></p>
    <div class="login">
       <!-- Div para a página de login -->
       <div class="login-page">
          <!-- Container para conteúdo -->
          <div class="container">
             <!-- Linha da grade -->
             <div class="row justify-content-center"> <!-- Centraliza o conteúdo horizontalmente -->
                <!-- Coluna responsiva -->
                <div class="col-lg-4 col-md-6 col-sm-8"> <!-- Ajusta o tamanho da coluna em diferentes dispositivos -->
                   <!-- Card de login -->
                   <div v-if="!registerActive" class="card login" v-bind:class="{ error: emptyFields }">
                      <!-- Logo -->
                      <div class="logo-container">
                         <img src="@/assets/logo.png" alt="Logo da sua empresa" class="logo">
                         <hr> <!-- Linha separadora -->
                      </div>
 
                      <!-- Título do card de login -->
                      <h2>Login</h2>
                      <!-- Formulário de login -->
                      <form class="form-group" @submit.prevent="doLogin">
                         <!-- Input de e-mail -->
                         <input v-model="login" type="text" class="form-control" placeholder="Login" required>
                         <br>
                         <!-- Input de senha -->
                         <input v-model="senha" type="password" class="form-control" placeholder="Senha" required>
                         <br>
                         <!-- Botão de envio do formulário de login -->
                         <button class="btn btn-primary" @click.prevent="logar">Conectar</button>
                         <br><br>
                         <!-- Link para redefinição de senha -->
                         <!--  <router-link to="/alterarsenha" target="_blank">Esqueceu a senha?</router-link>  -->
                      </form>
                   </div>
 
                  
                   
                </div>
             </div>
          </div>
       </div>
    </div>
 </template>

<script>
import LoginService from "@/services/LoginService";

export default {
    data() {
        return {
            login: "",
            senha: "",
        };
    },
    mounted() {

    },
    methods: {
        logar() {
            //console.log(this.login, this.senha)
            LoginService.logar(this.login, this.senha);
        },
    },
};
</script>

<style scoped>
/* Estilos para elementos de texto */
p {
   line-height: 1rem; /* Define a altura da linha */
}

/* Estilos para título h1 */
h1 {
   margin-bottom: 1.5rem; /* Define a margem inferior */
}

/* Estilos para cards */
.card {
   padding: 20px; /* Define o espaçamento interno */
}

/* Estilos para grupos de formulários */
.form-group {
   /* Estilos para inputs dentro do form-group */
   input {
      margin-bottom: 20px; /* Define a margem inferior */
      border-radius: 10px; /* Define o arredondamento dos cantos */
   }
}

/* Estilos para a página de login */
.login-page {
   align-items: center; /* Centraliza verticalmente os itens */
   display: flex; /* Define um layout flexível */
   height: flex; /* Define a altura como flexível */
}

.logo {
   width: 150px; /* Define a largura da logo */
   height: auto; /* Mantém a altura proporcional para evitar distorções */
}

.btn {
   box-shadow: 1px 2px 1px 1px rgba(0, 0, 0, 0.745) !important;
}

</style>
