import React from "react";
import { Route } from './components/Router';
import LinkBtn from './components/LinkBtn';
import Conferences from "./components/conferences";
import Authorization from "./components/authorization";
import Account from "./components/account";
import Registration from "./components/registration";
import CreateConference from "./components/creatConference";
import myInitObject from './index';

class App extends React.Component {
  constructor(props){
    super(props)
    this.state = {
      user_login: undefined,
      error: undefined
    };
    
  }

  componentDidCatch(){
    this.setState({user_login: myInitObject.user_login, user_id: myInitObject.user_id});
  }

  componentDidMount() {

      function getTwoDecimal(num) {
        return parseFloat(num.toFixed(2) + 0.5);
      }

      const mouse = {
        decimal(coord) {
          return getTwoDecimal(coord / 1000);
        },
        x(e) {
          return Math.abs(e.clientX - (window.innerWidth / 2));
        },
        y(e) {
          return Math.abs(e.clientY - (window.innerHeight / 2));
        }
      };

      const changeTextAlphaVal = (txt, e) => {
        const root = document.querySelector(":root");
        const cssVar = "--alpha";
        const currentAlpha = getComputedStyle(root)
        .getPropertyValue(cssVar)
        .trim();

        const max = parseFloat(currentAlpha);
        const dx = mouse.decimal(mouse.x(e));
        const dy = mouse.decimal(mouse.y(e));

        let alphaVal;
        if (dx <= 0) {
          alphaVal = dy >= max ? dy : getTwoDecimal(max - dy);
        } else {
          alphaVal = dx >= max ? dx : getTwoDecimal(max - dx);
        }

        txt.style.setProperty(cssVar, alphaVal);	
      };

      function createShadow(e, currTarget) {
        const walk = Math.round(Math.max(window.innerWidth, window.innerHeight)/6);
        const coordWalk = (coord, side) => Math.round(coord / side * walk - walk / 2);
        const xWalk = coordWalk(e.clientX, currTarget.offsetWidth);
        const yWalk = coordWalk(e.clientY, currTarget.offsetHeight);

        const pink = [255, 0, 139];
        const blue = [0, 86, 255];
        const yellow = [255, 240, 0];
        const typoAlpha = 0.6;

        const typo = currTarget.querySelector(".typo");
        changeTextAlphaVal(typo, e); 

        typo.style.textShadow = `
          ${xWalk}px ${yWalk}px 0 rgba(${pink}, ${typoAlpha}),
          ${xWalk * -1}px ${yWalk * 2}px 0 rgba(${blue}, ${typoAlpha}),
          ${xWalk * -2}px ${yWalk * -1}px 0 rgba(${yellow}, ${typoAlpha})
        `;
      }

      function onMouseMove(e) {
        createShadow(e, e.currentTarget);
      }
      function onTouchMove(e) {
        createShadow(e.changedTouches[0], e.currentTarget);
      }
      try{
      const heading = document.querySelector(".heading");
      heading.addEventListener("mousemove", onMouseMove);
      heading.addEventListener("touchmove", onTouchMove);
      }catch{
        
      }
  }

  render(){
    return(
      <div>
        <header>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav mr-auto">
              <li className="nav-item"><LinkBtn to="/" label={'Account'} /></li>
              <li className="nav-item"><LinkBtn to="/conference" label={'Conferences'} /></li>
              <li className="nav-item"><LinkBtn to="/authorization" label={'Authorization'}/></li>
              <li className="nav-item"><LinkBtn to="/about" label={'About'} /></li>
              </ul>
            </div>
            </nav>
        </header>
        <div className="wrapper">
          <div className="content">
          </div>
              <div className="container main">
                <Route exact path="/" component={Account} />
                <Route path="/conference" component={Conferences} />
                <Route path="/authorization" component={Authorization}/>
                <Route path="/registration" component={Registration}/>
                <Route path="/creatConference" component={CreateConference}/>
                <Route path="/about" render={() => 
                  <div className="jumbotron zukabout"><img class="ml-1" src="https://loading.io/assets/img/c/icon/fidget-white.svg"></img>
                    <div className="heading">
                      <h1 className="typo" contenteditable="true" spellcheck="false">by Zuk</h1>
                    </div>
                    <img className="ml-1" src="https://loading.io/assets/img/c/icon/fidget-white.svg"></img>
                  </div>} /> 
            </div>
        </div>
      </div>
      );
  }
}

export default App;