<template>
  <barra-navegacao></barra-navegacao>

  <div class="cadastro">
    <div class="row g-0 justify-content-center"> <!-- Centraliza o conteúdo horizontalmente -->
      <!-- Coluna responsiva -->
      <div class="col-lg-9 col-md-6 col-sm-8"> <!-- Ajusta o tamanho da coluna em diferentes dispositivos -->
        <!-- Card de login -->
        <div v-if="!registerActive" class="card login" v-bind:class="{ error: emptyFields }">

          <h1>Cadastro de Projetos:</h1>

          <fieldset>
            <form class="">
              <div class="row gx-3 gy-2 align-items-center p-2">
                <div class="col-sm-4 form-group">
                  <input v-model="projeto.descricao" type="text" class="form-control" placeholder="Projeto" required>
                </div>
                <div class="col-sm-4 form-group">
                  <input v-model="projeto.professorRepresentante" type="text" class="form-control" placeholder="Professor" required>
                </div>
              </div>
              <div class="row gx-3 gy-2 col-sm-2 align-items-center p-3">
                <p><button class="btn btn-primary" @click.prevent="salvar">Salvar</button></p>
              </div>
            </form>
          </fieldset>

          <table class="table table-dark table-striped">
            <thead>
              <tr>
                <th style="width: 40%;">Projeto:</th>
                <th style="width: 40%;">Professor:</th>
                <th style="width: 20%;">Atualizar Dados:</th>
              </tr>
            </thead>
            <tbody class="table-group-divider">
              <tr v-for="(u, i) in projetos" v-bind:key="i">
                <td @click="abreEdit(u.id)">{{ u.descricao }}</td>
                <td @click="abreEdit(u.id)">{{ u.professorRepresentante }}</td>
                <td>
                  <button class="btn btn-primary btn-sm" @click="editarProjeto(u.id)">Editar</button>
                  <button class="btn btn-danger btn-sm" @click="excluirProjeto(u.id)">Excluir</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BarraNavegacao from "@/components/BarraNavegacao.vue";
import AuthService from "@/services/AuthService";

export default {
  components: {
    BarraNavegacao,
  },
  data() {
    return {
      projetos: [],
      projeto: {
        descricao: null,
        professorRepresentante: null,
      },
    };
  },
  mounted() {
    console.log(AuthService.dados.token);
    this.getProjetos();
  },
  methods: {
    async getProjetos() {
      try {
        let r = await fetch("http://localhost:8080/projetos", {
          method: "GET",
          headers: { Authorization: `Bearer ${AuthService.dados.token}` },
        });
        r.json().then((j) => {
          this.projetos = j;
        });
      } catch (ex) {
        console.log("ERRO", ex);
      }
    },
    async salvar() {
      console.log(JSON.stringify(this.projeto));

      fetch("http://localhost:8080/projetos", {
        method: "POST",
        body: JSON.stringify(this.projeto),
        headers: {
          Authorization: `Bearer ${AuthService.dados.token}`,
          "Content-Type": "application/json",
        },
      })
        .then((r) => {
          if (r.status != 200 && r.status != 201) {
            r.json().then((j) => {
              console.log("ERRO", j);
            });
          }
          this.getProjetos();
        })
        .catch((e) => {
          console.log("ERRO", e);
        });
    },
    abreEdit(id) {
      this.$router.push(`/projetos/${id}`);
    },
    editarProjeto(id) {
      // Função para editar o projeto
      console.log(`Editar projeto ${id}`);
      this.abreEdit(id);
    },
    async excluirProjeto(id) {
      // Função para excluir o projeto
      console.log(`Excluir projeto ${id}`);
      try {
        let r = await fetch(`http://localhost:8080/projetos/${id}`, {
          method: "DELETE",
          headers: { Authorization: `Bearer ${AuthService.dados.token}` },
        });
        if (r.status === 200 || r.status === 204) {
          this.projetos = this.projetos.filter((projeto) => projeto.id !== id);
        } else {
          console.log("Erro ao excluir o projeto");
        }
      } catch (ex) {
        console.log("ERRO", ex);
      }
    },
  },
};
</script>

<style scoped>
.card {
  padding: 20px;
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

button:hover {
  color: black;
}

table * {
  border: solid 1px black;
}

table tbody tr {
  cursor: pointer;
}

.table button {
  width: 75px;
  margin: 2px;
}
</style>