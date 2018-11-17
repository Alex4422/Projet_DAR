import React from 'react';
import Card from '@material-ui/core/Card';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent'
import Typography from '@material-ui/core/Typography';
import CardActionArea from '@material-ui/core/CardActionArea';

class ShowCard extends React.Component {
    render() {
        let cardStyle = {
            backgroundColor: '#FFF',
            margin: 16,
        };

        return (
            <Card raised={true} style={cardStyle}>
                <CardActionArea onClick={() => {this.props.onClicked(this.props.name)}}>
                    <CardMedia style={{height: 220}}
                               component={"img"}
                               src={this.props.poster}/>
                    <CardContent>
                        <Typography style={{color: '#000'}}>
                            {this.props.name}
                        </Typography>
                    </CardContent>
                </CardActionArea>
            </Card>
        );
    }
}

export default ShowCard;