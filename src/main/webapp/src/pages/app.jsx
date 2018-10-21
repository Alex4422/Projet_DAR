import React from 'react';
import {Route, NavLink, HashRouter} from "react-router-dom";

import HomePage from './home';
import MenuBar from '../components/menuBar/menuBar';
import {LoginPage} from "./authentication";

const AppContext = React.createContext();

var context = {
    "userToken": "",
    "setUserToken": ""
};

class App extends React.Component {
    state = {
        userToken: "",
        setUserToken: (token) => {this.setState({userToken: token})}
    };

    render() {
        let divStyle = {
            display: 'flex',
            height: '100vh'
        };
        return (
            <AppContext.Provider value={this.state}>
                <HashRouter>
                    <div>
                        <MenuBar/>
                        <div className="content" style={divStyle}>
                            <Route exact path="/" component={HomePage}/>
                            <Route path="/login" component={LoginPage}/>
                        </div>
                    </div>
                </HashRouter>
            </AppContext.Provider>
        )
    }
}

export {App, AppContext};