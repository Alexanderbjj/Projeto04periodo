<template>
  <barra-navegacao></barra-navegacao>
  
    <div class="cadastro">
      <div class="row g-0 justify-content-center">
        <!-- Centraliza o conteúdo horizontalmente -->
        <!-- Coluna responsiva -->
        <div class="col-lg-6 col-md-6 col-sm-8">
          <!-- Ajusta o tamanho da coluna em diferentes dispositivos -->
          <!-- Card de login -->
          <div v-if="usuario" class="card login" :class="{ error: emptyFields }">
            <h1>Edição de Usuário:</h1>
            <fieldset>
              <legend>Dados do usuário:</legend>
              <form @submit.prevent="atualizaUsuario">
                <div class="row gx-3 gy-2 align-items-center p-2">
                  <div class="col-sm-4 form-group">
                    <label>Login</label>
                    <input v-model="usuario.login" type="text" class="form-control" disabled />
                  </div>
                  <div class="col-sm-4 form-group">
                    <label>Nome</label>
                    <input v-model="usuario.nome" type="text" class="form-control" />
                  </div>
                  <div class="col-sm-4 form-group">
                    <label>Sobrenome</label>
                    <input v-model="usuario.sobrenome" type="text" class="form-control" />
                  </div>
                  <div class="col-sm-4 form-group">
                    <label>Período</label>
                    <input v-model="usuario.periodo" min="1" max="10" type="number" class="form-control" />
                  </div>
                  <div class="col-sm-8 menu-drop form-group">
                    <label>Permissões</label>
                    <select v-model="usuario.permissao" class="form-select" aria-label="Permissões">
                      <option v-for="(permissao, index) in permissoes" :key="index" :value="permissao.role">
                        {{ permissao.label }}
                      </option>
                    </select>
                  </div>
                </div>
                <div class="button-container">
                  <button type="submit" class="btn btn-primary atualizar">Atualizar</button>
                  <button class="btn btn-danger excluir" @click.prevent="excluiUsuario">Excluir</button>
                  <button class="btn btn-primary voltar" @click="voltar">Voltar</button>
                </div>
              </form>
            </fieldset>
          </div>
        </div>
      </div>
    </div>
  </template>
  <script>
  import BarraNavegacao from "@/components/BarraNavegacao.vue";
  import AuthService from "@/services/AuthService";
  import axios from "axios";
  
  export default {
    components: {
      BarraNavegacao,
    },
    data() {
      return {
        usuario: null,
        permissoes: [
          { label: "Administrador", role: "ROLE_ADMIN" },
          { label: "Usuário", role: "ROLE_USER" },
          { label: "Projetos", role: "ROLE_PROJETOS" },
        ],
        emptyFields: false,
      };
    },
    mounted() {
      this.getUsuario(this.$route.params.id);
    },
    methods: {
      getUsuario(id) {
        axios({
          method: "GET",
          url: `http://localhost:8080/users/${id}`,
          headers: {
            Authorization: `Bearer ${AuthService.dados.token}`,
          },
        }).then((r) => {
          this.usuario = r.data;
        });
      },
      atualizaUsuario() {
        if (!this.usuario.nome || !this.usuario.sobrenome || !this.usuario.periodo || !this.usuario.permissao) {
          this.emptyFields = true;
          return;
        }
        this.emptyFields = false;
  
        axios({
          method: "PUT",
          url: `http://localhost:8080/users/${this.usuario.id}`,
          headers: {
            Authorization: `Bearer ${AuthService.dados.token}`,
            "Content-Type": "application/json",
          },
          data: JSON.stringify(this.usuario),
        }).then((r) => {
          console.log(JSON.stringify(this.usuario));
          console.log(r);
          this.$router.push("/usuarios");
        });
        
      },
      excluiUsuario() {
        axios({
          method: "DELETE",
          url: `http://localhost:8080/users/${this.usuario.id}`,
          headers: {
            Authorization: `Bearer ${AuthService.dados.token}`,
          },
        }).then((r) => {
          console.log(r);
          this.$router.push("/usuarios");
        });
      },
      voltar() {
        this.$router.go(-1);
      },
    },
  };
  </script>
  <style scoped>
  .card {
    padding: 20px;
    /* Define o espaçamento interno */
  }
  
  fieldset {
    border: 2px solid rgb(0, 0, 0);
    /* Ajuste a borda conforme necessário */
    padding: 20px;
    /* Ajuste o espaçamento interno conforme necessário */
    border-radius: 10px;
    /* Ajuste o arredondamento dos cantos conforme necessário */
    margin-top: 20px;
    /* Ajuste a margem superior conforme necessário */
  }
  
  legend {
    font-size: 1.2em;
    /* Ajuste o tamanho da fonte conforme necessário */
    font-weight: bold;
    /* Deixe o texto em negrito */
  }
  
  textarea {
    width: 100%;
    /* Faz com que o textarea ocupe a largura total */
    height: 100px;
    /* Ajuste a altura conforme necessário */
    margin-top: 10px;
    /* Ajuste a margem superior conforme necessário */
    border-radius: 5px;
    /* Ajuste o arredondamento dos cantos conforme necessário */
    padding: 10px;
    /* Ajuste o espaçamento interno conforme necessário */
  }
  
  button {
    /* Ajuste o tamanho da fonte conforme necessário */
    box-shadow: 1px 2px 1px 1px rgba(0, 0, 0, 0.745) !important;
  }
  
  .atualizar:hover {
    color: black;
  }
  
  .excluir:hover {
    color: black;
  }
  
  .voltar:hover {
    color: black;
  }
  
  .menu-drop select {
    font-size: 0.9em;
    /* Ajuste o tamanho da fonte conforme necessário */
    margin-left: 10px;
    /* Ajuste a margem à esquerda conforme necessário */
    width: 100%;
    /* Largura padrão de 100% */
  }
  
  .table * {
    border: solid 1px black;
  }
  
  .button-container {
    display: flex;
    justify-content: space-between;
    /* Distribui os botões com espaço entre eles */
    gap: 10px;
    /* Espaçamento entre os botões */
  }
  
  .button-container button {
    flex: 1;
    /* Faz com que os botões tenham o mesmo tamanho */
  }
  </style>