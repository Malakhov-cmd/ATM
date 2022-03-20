<template>
  <div class="main-app-content-page">
    <header class="mb-auto">
      <div>
        <h3 class="float-md-start mb-0">ATM</h3>
        <h5 class="float-md-start mb-0 user-name">{{ name }}</h5>
        <nav class="nav nav-masthead justify-content-center float-md-end">
          <a class="nav-link" href="#">logout</a>
        </nav>
      </div>
    </header>

    <main class="px-3">
      <h1>Enter card data</h1>
      <div class="user-cards">

      </div>

      <div class="user-add-card">
        <b-button
            :class="isCollapsedZoneUserAddCardVisible ? null : 'collapsed'"
            :aria-expanded="isCollapsedZoneUserAddCardVisible ? 'true' : 'false'"
            aria-controls="collapse-4"
            @click="isCollapsedZoneUserAddCardVisible = !isCollapsedZoneUserAddCardVisible"
        >
          Toggle Collapse
        </b-button>
        <b-collapse id="collapse-4" v-model="isCollapsedZoneUserAddCardVisible" class="mt-2">
          <b-form class="form-of-adding-new-card nepmorphism">
            <b-form-group
                class="card-number-form-input"
                id="input-group-1"
                label="Card number:"
                label-for="card-input-1">
              <div class="inputs-card-number">

                <b-form-input
                    class="input-card-number"
                    id="card-input-1"
                    v-model="card.number.first"
                    placeholder="Enter card number"
                    required
                    type="number"
                ></b-form-input>

                <b-form-input
                    class="input-card-number"
                    id="card-input-2"
                    v-model="card.number.second"
                    placeholder="Enter card number"
                    required
                    type="number"
                ></b-form-input>

                <b-form-input
                    class="input-card-number"
                    id="card-input-3"
                    v-model="card.number.third"
                    placeholder="Enter card number"
                    required
                    type="number"
                ></b-form-input>

                <b-form-input
                    class="input-card-number"
                    id="card-input-4"
                    v-model="card.number.fourth"
                    placeholder="Enter card number"
                    required
                    type="number"
                ></b-form-input>
              </div>
            </b-form-group>

            <b-form-group
                class="card-date-valid-form-input"
                id="input-group-2"
                label="Month/Year:"
                label-for="card-input-2">
              <div class="inputs-card-date-valid">
                <b-form-input
                    class="input-card-number"
                    id="card-input-2"
                    v-model="card.date.month"
                    placeholder="Enter month"
                    required
                    type="number"
                ></b-form-input>

                <h4 class="input-card-number">/</h4>

                <b-form-input
                    class="input-card-number"
                    id="card-input-2"
                    v-model="card.date.year"
                    placeholder="Enter year"
                    required
                    type="number"
                ></b-form-input>
              </div>
            </b-form-group>

            <b-form-group
                class="inputs-owner-name"
                id="input-group-3"
                label="Owner name:"
                label-for="card-input-3">
              <b-form-input
                  class="input-owner-name"
                  id="card-input-3"
                  v-model="card.owner"
                  placeholder="Enter owner name"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group
                class="inputs-cvv"
                id="input-group-4"
                label="CVV code:   "
                label-for="card-input-4">
              <b-form-input
                  class="input-cvv"
                  id="card-input-4"
                  v-model="card.cvv"
                  placeholder="Enter cvv code"
                  required
                  type="number"
              ></b-form-input>
            </b-form-group>

            <div class="btm-card-add-submit">
              <b-button variant="outline-success" v-on:click="addCard">
                Add
              </b-button>
            </div>

          </b-form>
        </b-collapse>
      </div>
    </main>

    <footer class="mt-auto text-white-50">
      <p>App created by
        <a href="https://github.com/Malakhov-cmd" class="text-white">
          @Malakhov-cmd
        </a>
        .
      </p>
    </footer>
  </div>
</template>

<script>
import axios from "axios";

let isSentAndReceived = false

export default {
  name: "Home",
  data() {
    return {
      name: frontendData.username,
      isAnyCardsExist: false,

      isCollapsedZoneUserAddCardVisible: false,
      card: {
        number: {
          first: '',
          second: '',
          third: '',
          fourth: ''
        },
        date: {
          month: '',
          year: ''
        },
        owner: '',
        cvv: ''
      },
    }
  },
  methods: {
    toastCreation(text) {
      this.$toasted.error(text, {
        position: 'top-right',
        duration: 5000,
        fullWidth: false,
        action: {
          text: 'Cancel',
          onClick: (e, toastObject) => {
            toastObject.goAway(0);
          }
        },
      })
    },

    addCard() {
      axios.post('/card/add?number=' + this.card.number.first + this.card.number.second + this.card.number.third + this.card.number.fourth
          + '&dateValid=' + this.card.date.month + this.card.date.year
          + '&owner=' + this.card.owner
          + '&CVV=' + this.card.cvv
          + '&username=' + frontendData.username
          + '&password=' + frontendData.password)
          .then(function (response) {
            if (response.data !== null) {
              isSentAndReceived = true

              console.log(response.data)
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      const interval = setInterval(() => {
        if (isSentAndReceived) {


          clearInterval(interval)
        } else {
          this.toastCreation("You probably enter invalid data")
          clearInterval(interval)
        }
      }, 1000)
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.main-app-content-page {
  min-height: 100vh;
  min-width: 100vw;
  display: flex;
  flex-direction: column;
  align-content: space-around;
  justify-content: space-around;
  padding: 45px;
}

.btn-secondary,
.btn-secondary:hover,
.btn-secondary:focus {
  color: #333;
  text-shadow: none;
}

body {
  text-shadow: 0 .05rem .1rem rgba(0, 0, 0, .5);
  box-shadow: inset 0 0 5rem rgba(0, 0, 0, .5);
}

.nav-masthead .nav-link {
  padding: .25rem 0;
  font-weight: 700;
  color: rgba(255, 255, 255, .5);
  background-color: transparent;
  border-bottom: .25rem solid transparent;
}

.nav-masthead .nav-link:hover,
.nav-masthead .nav-link:focus {
  border-bottom-color: rgba(255, 255, 255, .25);
}

.nav-masthead .nav-link + .nav-link {
  margin-left: 1rem;
}

.nav-masthead .active {
  color: #fff;
  border-bottom-color: #fff;
}

.user-name {
  margin-left: 5vw;
}

.nepmorphism {
  border-radius: 50px;

  box-shadow: 10px 10px 21px #000000,
  -10px -10px 21px #3d3c3c;
}

.form-of-adding-new-card {
  margin-top: 47px;
  padding: 45px;
}

.card-number-form-input {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 27px;
}

.inputs-card-number {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  margin-left: 200px;
}

.input-card-number {
  margin-left: 10px;
}

.card-date-valid-form-input,
.inputs-owner-name,
.inputs-cvv {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 25px;
}

.inputs-card-date-valid {
  display: flex;
  align-items: center;
  margin-left: 210px;
}

.input-owner-name {
  margin-left: 210px;
}

.input-cvv {
  margin-left: 230px;
}
</style>