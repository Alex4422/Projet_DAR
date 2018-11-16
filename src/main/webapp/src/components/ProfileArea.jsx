import React from 'react';
import PropTypes from 'prop-types'

const ProfileArea = (props) => {
    return (
        <div>


            <h1>Votre profil</h1>

            <ul>
                <li>Login : {props.username}</li>
                <li>Adresse mail: {props.emailAddress}</li>
            </ul>
        </div>
    )
};

ProfileArea.propTypes = {
    username: PropTypes.string.isRequired,
    emailAddress: PropTypes.string.isRequired
};

export default ProfileArea;