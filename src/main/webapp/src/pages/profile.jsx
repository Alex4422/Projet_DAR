import React from 'react';
import FormControl from "@material-ui/core/FormControl/FormControl";
import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import Input from "@material-ui/core/Input/Input";
import FormHelperText from "@material-ui/core/FormHelperText/FormHelperText";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";



class Profile extends React.Component {

    render() {

        return (

            <div>
                <FormControl >
                    <h4> nom : </h4>
                    <br/><br/>

                </FormControl>
                <FormControl>
                    <h4> prénom : </h4>
                    <br/><br/>

                </FormControl>

                <FormControl>
                <h4> login : </h4>
                <br/><br/>

                </FormControl>

            <FormControl>
        <h4> password : </h4>
    <br/><br/>

        </FormControl>
            </div>


        );

    }
}

export default Profile;