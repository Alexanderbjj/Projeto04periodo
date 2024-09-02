<template>
    <div class="home">
        <barra-navegacao></barra-navegacao>

        <div class="d-flex align-items-center justify-content-center min-vh-70">
            <div class="col-lg-4 col-md-6 col-sm-8">
                <div v-if="!registerActive" class="card login" :class="{ error: emptyFields }">
                    <fieldset>
                        <form>
                            <div class="col-sm-12 form-group p-2 text-center">
                                <p class="fs-5 fw-bold" style="font-size: 30px;">Seja bem-vindo!</p>
                                <div class="log-user">{{ logado.login }}</div>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// @ is an alias to /src

import LoginService from "@/services/LoginService";
import AuthService from "@/services/AuthService";
import BarraNavegacao from '@/components/BarraNavegacao.vue';

export default {
    name: "HomeView",
    components: {
        BarraNavegacao
    },
    data() {
        return {
            registerActive: false,
            emptyFields: false
        };
    },
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
.home {
    position: relative;
}

.card {
    padding: 10px;
    /* Define o espaçamento interno */
    max-width: 100%; /* Para que a caixa não fique maior que a tela */
    margin: auto; /* Centraliza a caixa horizontalmente */
}

.d-flex {
    display: flex;
}

.align-items-center {
    align-items: center;
}

.justify-content-center {
    justify-content: center;
}

.min-vh-100 {
    min-height: 100vh;
}

.log-user {
    font-weight: bold; 
    font-family: Helvetica;
    text-transform: uppercase;
    font-size: 25px;    
    color: black;
    text-justify: center;
}
</style>