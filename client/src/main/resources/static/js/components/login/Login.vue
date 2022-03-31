<template>
  <div class="main-login">
    <div class="main-login-functional-section">
      <p class="login-label">Login in ATM</p>

      <div class="social-auth-btns">
        <p>Login via social</p>
        <b-btn class="google-oath-btn social-auth-btn"
               v-on:click="googleOauth">
          <b-icon-google font-scale="2"></b-icon-google>
        </b-btn>
        <b-btn class="google-oath-btn social-auth-btn"
               v-on:click="githubOauth">
          <b-icon-github font-scale="2"></b-icon-github>
        </b-btn>
      </div>
    </div>

    <footer class="mt-auto text-white-50 login-footer">
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
  name: "Login",
  computed: {
    checkUsernameCorrect() {
      return this.usernameInputed.length >= 4
    },
    checkUserPasswordCorrect() {
      return this.passwordInputed.length >= 4
    },
    invalidFeedback() {
      if (this.usernameInputed.length > 0) {
        return 'Enter at least 4 characters.'
      }
      return 'Please enter valid data.'
    }
  },
  data() {
    return {
      usernameInputed: '',
      passwordInputed: ''
    }
  },
  methods: {
    isSubmitAvailable() {
      return this.checkUsernameCorrect && this.checkUserPasswordCorrect
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

    defaultAuth() {
      if (this.isSubmitAvailable()) {
        axios.post('/login/default?username=' + this.usernameInputed
            + '&password=' + this.passwordInputed)
            .then(function (response) {
              isSentAndReceived = true

              window.frontendData = response.data
            })
            .catch(function (error) {
              console.log(error);
            })
        const interval = setInterval(() => {
          if (isSentAndReceived) {

            if (frontendData.username === ""
                && frontendData.password === "") {
              this.toastCreation("You enter invalid data")
            } else {
              location.href = "/#/home"
            }

            clearInterval(interval)
          }
        }, 1000)
      } else {
        this.toastCreation("Input some data first")
      }
    },

    googleOauth() {
      location.href = '/oauth2/authorization/google'
    },
    githubOauth() {
      location.href = '/oauth2/authorization/github'
    }
  },
  mounted() {
    if (frontendData !== null) {
      if (frontendData.username !== "" && frontendData.password !== ""
          && frontendData.username !== null && frontendData.password !== null) {
        location.href = "/#/home"
      }
    }
  }
}
</script>

<style scoped>

.main-login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  min-height: 100vh;
}

.main-login-functional-section{
  margin-top: 35vh;
}

.login-footer{
  margin-bottom: 45px;
}
.login-inputs {
  min-width: 20vw;
}

.input-row-username,
.input-row-password {
  margin-bottom: 25px;
}

.social-auth-btns {
  margin-top: 25px;
  margin-bottom: 25px;
}


.social-auth-btn {
  background: none;
  border: none;
}

.login-label {
  font-size: 25px;
  font-family: sans-serif;
  margin-bottom: 17px;
}

.toasted-container {
  position: absolute;
}
</style>