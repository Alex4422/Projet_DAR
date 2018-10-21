import React from 'react';
import Button from '@material-ui/core/Button'
import Card from '@material-ui/core/Card'
import TextField from "@material-ui/core/TextField/TextField";

class AuthenticationPage extends React.Component {
    render() {
        let divStyle =  {
            display: 'flex',
            flexGrow: 1,
            justifyContent: 'center',
            alignItems: 'center',
            background: "url(\"https://tel.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2FTEL.2Enews.2F2018.2F01.2F21.2F31cfc978-94a6-4b49-ab14-acfb0d05c9ff.2Ejpeg/1150x495/crop-from/top/breaking-bad-bryan-cranston-evoque-la-scene-la-plus-difficile-qu-il-a-tournee.jpg\")",
            backgroundSize: 'cover',
        };

        let cardStyle = {
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            flexDirection: 'column',
            margin: 'auto',
            width: 400,
            padding: 24,
        };

        return (
            <div style={divStyle}>
                <Card style={cardStyle}>
                    {this.props.children}
                </Card>
            </div>
        );
    }
}

const LoginPage = () => {
    let textFieldStyle = {
        marginBottom: 16
    };

    return (
        <AuthenticationPage>
            <TextField style={textFieldStyle} variant="outlined" label="Username"/>
            <TextField style={textFieldStyle} variant="outlined" label="Password" type='password'/>
            <Button variant="contained" color="primary">
                Login
            </Button>
        </AuthenticationPage>
    );
}

export {AuthenticationPage, LoginPage}