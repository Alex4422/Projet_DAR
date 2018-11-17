import React from 'react'

const urlImage = 'https://archive-media-1.nyafuu.org/wg/image/1412/53/1412539919678.jpg';

class HomePage extends React.Component {
    render() {
        return (
            <div style={this.homePageStyle()}>
                <h1 style={this.sloganStyle()}>Browse, rate and comment all your favorite series !</h1>
            </div>
        );
    }

    homePageStyle() {
        return {
            display: 'flex',
            flexDirection: 'row',
            flexGrow: 1,
            justifyContent: 'center',
            backgroundSize: 'cover',
            backgroundImage: "url(" + urlImage + ")"
        }
    }

    sloganStyle(){
        return {
            textAlign: 'center',
            color: 'white',
            fontStyle: 'italic'
        }
    }

}

export default HomePage;