import React from 'react';
import DefaultPage from './default'
import {SignInSignUp, SignUp} from "../components/signin_signup";

class MainPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            title: "SuperSeries",
            content: <SignInSignUp onSignUp={() => this.showSignup()}/>,
        };
    }

    render() {
        return (
            <DefaultPage title={this.state.title}>
                {this.state.content}
            </DefaultPage>
        );
    }

    showSignInSignUp() {
        this.setState({
            title: "SuperSeries",
            content: <SignInSignUp onSignUp={() => this.showSignup()}/>,
        })
    }

    showSignup() {
        this.setState({
            title: 'Sign Up',
            content: <SignUp/>,
        });
        this.render()
    }
}

export default MainPage;