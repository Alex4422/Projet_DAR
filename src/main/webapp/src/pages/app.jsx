import React from 'react';
import {Route, NavLink, HashRouter} from "react-router-dom";

import HomePage from './home';
import RoutedMenuBar from '../components/menuBar/menuBar';

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
                        <RoutedMenuBar/>
                        <div className="content" style={divStyle}>
                            <Route exact path="/" component={HomePage}/>
                        </div>
                    </div>
                </HashRouter>
            </AppContext.Provider>
        )
    }
}

export {App, AppContext};