import React from "react";
import { Route } from './components/Router';
import LinkBtn from './components/LinkBtn';
import Conferences from "./components/conferences";
import Authorization from "./components/authorization";
import Account from "./components/account";
import Registration from "./components/registration";
import CreateConference from "./components/creatConference";
import About from "./components/about";

class App extends React.Component {

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
                <Route path="/about" component={About} />
            </div>
        </div>
      </div>
      );
  }
}

export default App;
