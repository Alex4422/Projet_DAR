import React from 'react';
import Button from '@material-ui/core/Button'
import Card from '@material-ui/core/Card'

class DefaultPage extends React.Component {
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
            justifyContent: 'space-evenly',
            alignItems: 'center',
            flexDirection: 'row',
            margin: 'auto',
            width: 400,
        };

        return (
            [<DefaultHeader {...this.props}/>,
            <div style={divStyle}>
                <Card style={cardStyle} raised="true" square="true" component="div">
                    {this.props.children}
                </Card>
            </div>]
        );
    }
}

class DefaultHeader extends React.Component {
    render() {
        let divStyle = {
            backgroundColor: "#222",
            height: 100,
            display: 'flex',
            flexDirection: 'column',
        };

        let titleStyle = {
            color: "#FFF",
            fontSize: 48,
            weight: 'bold',
            margin: 'auto'
        };

        return (
            <div style={divStyle}>
                <p style={titleStyle}>{this.props.title}</p>
            </div>
        );
    }
}

export default DefaultPage