<template>
  <div class="main-app-content-page">
    <header class="mb-auto">
      <div>
        <h3 class="float-md-start mb-0">ATM</h3>
        <h5 class="float-md-start mb-0 user-name">{{ name }}</h5>
        <nav class="nav nav-masthead justify-content-center float-md-end" v-on:click="">
          <a class="nav-link" v-on:click="logout">logout</a>
        </nav>
      </div>
    </header>

    <main class="px-3 main">
      <h1>Enter card data</h1>

      <div class="user-add-card">
        <b-button
            :class="isCollapsedZoneUserAddCardVisible ? null : 'collapsed'"
            :aria-expanded="isCollapsedZoneUserAddCardVisible ? 'true' : 'false'"
            aria-controls="collapse-4"
            @click="isCollapsedZoneUserAddCardVisible = !isCollapsedZoneUserAddCardVisible"
        >
          Open card input fields
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
              <b-button variant="success" v-on:click="addCard">
                Add
              </b-button>
            </div>

          </b-form>
        </b-collapse>
      </div>

      <div class="user-cards-zone">
        <div v-if="userCards === null">
          <h2 class="user-cards-label">There are no cards yet</h2>
        </div>
        <div v-if="userCards !== null">
          <h2 class="user-cards-label">
            Your cards
          </h2>
          <div class="user-cards">
            <div class="user-card nepmorphism" v-for="(value) in userCards">
              <div class="user-card-element">
                <h5>Number: </h5>
                <div class="user-card-number">{{ value.number }}</div>
              </div>
              <div class="user-card-element">
                <h5>Data valid: </h5>
                <div class="user-card-date-valid">{{ dateToStringValue(value.dateValid) }}</div>
              </div>
              <div class="user-card-element">
                <h5>Owner name: </h5>
                <div class="user-card-owner">{{ value.owner }}</div>
              </div>
              <div class="user-card-element">
                <h5>Balance: </h5>
                <div class="user-card-balance">{{ value.balance }} ₽</div>
              </div>

              <div class="user-card-redirect-btn">
                <router-link :to="/card/ + value.number + /user/ + name">
                  <b-button variant="success">
                    Choose
                  </b-button>
                </router-link>
              </div>
            </div>
          </div>
        </div>
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
let newCard = null

let userCards = null

export default {
  name: "Home",
  data() {
    return {
      name: frontendData.username,
      isAnyCardsExist: false,

      addedCard: null,

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

      userCards: []
    }
  },
  methods: {
    cardFieldCheck() {
      return (
          this.card.number.first.length === 4 &&
          this.card.number.second.length === 4 &&
          this.card.number.third.length === 4 &&
          this.card.number.fourth.length === 4 &&

          this.card.date.month.length === 2 &&
          this.card.date.year.length === 2 &&

          this.card.owner.length > 0 &&
          this.card.cvv.length === 3);
    },

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

    dateToStringValue(value) {
      if (value.toString().length === 3) {
          return "0" + value.toString().substring(0,1) + " / " + value.toString().substring(1, value.toString().length)
      } else {
        return value.toString().substring(0,2) + " / " + value.toString().substring(2, value.toString().length)
      }
    },

    cleanInputsFields() {
      this.card.number.first = ''
      this.card.number.second = ''
      this.card.number.third = ''
      this.card.number.fourth = ''

      this.card.date.month = ''
      this.card.date.year = ''

      this.card.owner = ''
      this.card.cvv = ''

      this.isCollapsedZoneUserAddCardVisible = false
    },

    addCard() {
      if (this.cardFieldCheck()) {
        axios.post('/card/add?number=' + this.card.number.first + this.card.number.second + this.card.number.third + this.card.number.fourth
            + '&dateValid=' + this.card.date.month + this.card.date.year
            + '&owner=' + this.card.owner
            + '&CVV=' + this.card.cvv
            + '&username=' + frontendData.username)
            .then(function (response) {
              isSentAndReceived = true
              newCard = response.data
            })
            .catch(function (error) {
              console.log(error)
            })
        const interval = setInterval(() => {
          if (isSentAndReceived) {
            this.addedCard = newCard

            if (this.addedCard.owner === null || this.addedCard.number === null) {
              this.toastCreation("You enter wrong data. Please check your inputs")
            } else {
              this.toastCreation("Card successfully added")

              this.saveNewCardToLocalData()

              this.cleanInputsFields()
            }

            clearInterval(interval)
          }
        }, 1000)
      } else {
        this.toastCreation("Enter some data first")
      }
    },

    saveNewCardToLocalData() {
      this.userCards[this.userCards.length] = {
        balance: this.addedCard.balance,
        cvv: this.addedCard.cvv,
        dateValid: this.addedCard.dateValid,
        number: this.addedCard.number,
        operationDTOList: this.addedCard.operationDTOList,
        owner: this.addedCard.owner,
        username: this.addedCard.username
      }
    },

    logout() {
      axios.post('/logout')
          .then(function (response) {
            window.location = "/"
          })
          .catch(function (error) {
            console.log(error)
          })
    }
  },
  mounted() {
    axios.get('/card/get/all', {
      params: {
        username: frontendData.username
      }
    })
        .then(function (response) {
          isSentAndReceived = true
          userCards = response.data
        })
        .catch(function (error) {
          console.log(error)
        })
    const interval = setInterval(() => {
      if (isSentAndReceived) {
        this.userCards = userCards

        clearInterval(interval)
      }
    }, 10)
  }
}
</script>

<style scoped>
.main {
  overflow: auto;
  max-height: 80vh;
}

.main-app-content-page {
  min-height: 100vh;
  min-width: 100vw;
  display: flex;
  flex-direction: column;
  align-content: space-around;
  justify-content: space-around;
  padding: 45px;
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

.main::-webkit-scrollbar {
  width: 7px;
  height: 7px;
}

.main::-webkit-scrollbar-thumb {
  border-width: 1px 1px 1px 2px;
  border-color: #9f1b1b;
  background-color: #aaa;
  border-radius: 7px;
}

.main::-webkit-scrollbar-thumb:hover {
  border-width: 1px 1px 1px 2px;
  border-color: #555;
  background-color: #777;
}

.main::-webkit-scrollbar-track {
  border-width: 0;
}

.main::-webkit-scrollbar-track:hover {
  border-left: solid 1px #aaa;
  background-color: #eee;
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

.user-cards {
  padding: 45px;
}

.user-cards-label {
  margin-top: 55px;
}

.user-card {
  margin-bottom: 75px;
  padding: 55px;
}

.user-card-element {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}

.user-card-number {
  margin-left: 175px;
}

.user-card-date-valid {
  margin-left: 160px;
}

.user-card-owner {
  margin-left: 135px;
}

.user-card-balance {
  margin-left: 180px;
}
</style>