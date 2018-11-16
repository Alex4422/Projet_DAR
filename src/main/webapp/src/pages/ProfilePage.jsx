import React, {PropTypes} from 'react';
import ProfileArea from '../components/ProfileArea';
//import {connect} from 'react-redux';

class ProfilePage extends React.Component {

    componentDidMount() {
        //const userId = this.props.pageState.auth.id;
    }



    render() {
        let ProfileStyle = {
            backgroundColor: '#FFF',
            marginLeft: 80,
            marginTop:33
        };
        return (
            <div style={ProfileStyle}>
                <ProfileArea
                    username="peter"
                    emailAddress="peter@whatever.com"
                />
            </div>
        );
    }

}

ProfilePage.propsTypes = {
    //dispatch: PropTypes.func.isRequired,
    //pageState: PropTypes.object.isRequired
};




export default ProfilePage;