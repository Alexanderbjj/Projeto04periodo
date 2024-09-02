<template>
    <div class="naoautorizado">
        <div class="row g-0 justify-content-center">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div v-if="!registerActive" class="card login" v-bind:class="{ error: emptyFields }">
                    <fieldset>
                        <form class="">
                            <h1>Não autorizado</h1>
                            <div class="row gx-3 gy-2 align-items-center p-2">
                                <button class="btn btn-primary voltar-btn" @click="retornarInicio">Voltar para o
                                    início</button>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>
        </div>

    </div>
</template>

<script>

export default {
    components: {

    },
    methods: {
        retornarInicio() {
            this.$router.push("/");
        },
    },
};
</script>

<style scoped>
h1 {
    text-align: center;
}

.card {
    padding: 10px;
    /* Define o espaçamento interno */
}

.naoautorizado {
    margin-top: 25px;
}

</style>