import React from 'react'
import InputBase from '@material-ui/core/InputBase';
import SearchIcon from '@material-ui/icons/Search';
import { fade } from '@material-ui/core/styles/colorManipulator';


class SearchField extends React.Component {
    constructor (props) {
        super(props)
        this.state = { searchValue: "" }
    }

    render() {
        let searchFieldRoot = {
            display: 'flex',
            alignItems: 'center',
            borderRadius: 4,
            backgroundColor: fade('#FFF', 0.15),
            width: '300px'
        };
        return (
           <div style={searchFieldRoot}>
               <SearchIcon style={{margin: 8}}/>
               <InputBase placeholder="Search a show..."
                          onChange={(event) => this.setState({searchValue: event.target.value})}
                          onKeyDown={this.handleKeyDown.bind(this)}/>
           </div>
        );
    }

    handleKeyDown(event) {
        if (event.keyCode === 13) {
            if (this.props.onSearch != null) {
                this.props.onSearch(this.state.searchValue)
            }
        }
    }
}

export default SearchField;

