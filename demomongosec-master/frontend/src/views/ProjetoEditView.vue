<template>
    <barra-navegacao></barra-navegacao>
    
    <div class="cadastro">
      <div class="row g-0 justify-content-center">
        <!-- Centraliza o conteúdo horizontalmente -->
        <!-- Coluna responsiva -->
        <div class="col-lg-9 col-md-6 col-sm-8">
          <!-- Ajusta o tamanho da coluna em diferentes dispositivos -->
          <!-- Card de edição de projeto -->
          <div v-if="projeto" class="card login" :class="{ error: emptyFields }">
            <h1>Edição de Projeto:</h1>
            <fieldset>
              <legend>Dados do projeto:</legend>
              <form @submit.prevent="atualizaProjeto">
                <div class="row gx-3 gy-2 align-items-center p-2">
                  <div class="col-sm-6 form-group">
                    <input v-model="projeto.descricao" type="text" class="form-control" placeholder="Descrição" required />
                  </div>
                  <div class="col-sm-6 form-group">
                    <input v-model="projeto.professorRepresentante" type="text" class="form-control" placeholder="Professor Representante" required />
                  </div>
                </div>
                <div class="row gx-3 gy-2 col-sm-2 align-items-center p-3">
                  <p><button class="btn btn-primary" type="submit">Atualizar</button></p>
                  <p><button class="btn btn-danger" @click.prevent="excluiProjeto">Excluir</button></p>
                  <p><button class="btn btn-primary" @click="voltar">Voltar</button></p>
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
        projeto: null,
        emptyFields: false,
      };
    },
    mounted() {
      this.getProjeto(this.$route.params.id);
    },
    methods: {
      async getProjeto(id) {
        try {
          let response = await axios.get(`http://localhost:8080/projetos/${id}`, {
            headers: {
              Authorization: `Bearer ${AuthService.dados.token}`,
            },
          });
          this.projeto = response.data;
        } catch (error) {
          console.error("Erro ao buscar projeto:", error);
        }
      },
      async atualizaProjeto() {
        if (!this.projeto.descricao || !this.projeto.professorRepresentante) {
          this.emptyFields = true;
          return;
        }
        this.emptyFields = false;
  
        try {
          await axios.put(`http://localhost:8080/projetos/${this.projeto.id}`, this.projeto, {
            headers: {
              Authorization: `Bearer ${AuthService.dados.token}`,
              "Content-Type": "application/json",
            },
          });
          this.$router.push("/projetos");
        } catch (error) {
          console.error("Erro ao atualizar projeto:", error);
        }
      },
      async excluiProjeto() {
        try {
          await axios.delete(`http://localhost:8080/projetos/${this.projeto.id}`, {
            headers: {
              Authorization: `Bearer ${AuthService.dados.token}`,
            },
          });
          this.$router.push("/projetos");
        } catch (error) {
          console.error("Erro ao excluir projeto:", error);
        }
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
  }
  
  fieldset {
    border: 2px solid rgb(0, 0, 0);
    padding: 20px;
    border-radius: 10px;
    margin-top: 20px;
  }
  
  legend {
    font-size: 1.2em;
    font-weight: bold;
  }
  
  .form-control {
    height: 30px;
    font-size: 0.9em;
    margin-left: 10px;
  }
  
  .form-group input {
    margin-bottom: 20px !important;
    border-radius: 10px !important;
  }
  
  button {
    box-shadow: 1px 2px 1px 1px rgba(0, 0, 0, 0.745) !important;
  }
  
  .btn:hover {
    color: black;
  }

  </style>
  