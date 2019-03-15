import React from "react";
import styled from "styled-components";
import { BaseContainer } from "../../helpers/layout";
import { getDomain } from "../../helpers/getDomain";
import User from "../shared/models/User";
import { withRouter } from "react-router-dom";
import { Button } from "../../views/design/Button";
import Player from "../../views/Player";

const FormContainer = styled.div`
  margin-top: 2em;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 300px;
  justify-content: center;
`;

const Form = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 60%;
  height: 375px;
  font-size: 16px;
  font-weight: 300;
  padding-left: 37px;
  padding-right: 37px;
  border-radius: 5px;
  background: linear-gradient(rgb(27, 124, 186), rgb(2, 46, 101));
  transition: opacity 0.5s ease, transform 0.5s ease;
`;

const InputField = styled.input`
  &::placeholder {
    color: rgba(255, 255, 255, 0.2);
  }
  height: 35px;
  padding-left: 15px;
  margin-left: -4px;
  border: none;
  border-radius: 20px;
  margin-bottom: 20px;
  background: rgba(255, 255, 255, 0.2);
  color: white;
`;

const Label = styled.label`
  color: white;
  margin-bottom: 10px;
  text-transform: uppercase;
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;


/**
 * Classes in React allow you to have an internal state within the class and to have the React life-cycle for your component.
 * You should have a class (instead of a functional component) when:
 * - You need an internal state that cannot be achieved via props from other parent components
 * - You fetch data from the server (e.g., in componentDidMount())
 * - You want to access the DOM via Refs
 * https://reactjs.org/docs/react-component.html
 * @Class
 */

class EditProfile extends React.Component {

    /**
     * If you don’t initialize the state and you don’t bind methods, you don’t need to implement a constructor for your React component.
     * The constructor for a React component is called before it is mounted (rendered).
     * In this case the initial state is defined in the constructor. The state is a JS object containing two fields: name and username
     * These fields are then handled in the onChange() methods in the resp. InputFields
     */

    constructor() {
        super();
        this.state = {
            username: null,
            birthday: null,
            user: new User( {"username" : "dummy", "id": 0, "birthday": 0, "status": "temp"} )
        };
    }

    cancelChanges(){
        this.props.history.push(`/profile/`+localStorage.getItem("thisUserID"));
    }

    save(){
        var date  = new Date(this.state.birthday);
        var bday = "";
        if (isNaN(date.getTime())) {
            alert("invalid Date format, please use yyyy-mm-dd");
            return;
        }
        else {
            bday = date.toISOString().substring(0,10);
        }
        var text = JSON.stringify({
            username: this.state.username,
            birthday: bday
        });

        var id = localStorage.getItem("selectedID");

        fetch(`${getDomain()}/users/` + id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: text
        }).then(response => {
            this.props.history.push(`/profile/`+localStorage.getItem("thisUserID"));

        }).catch(err => {
            console.log(err);
            alert("Something went wrong editing the user info: " + err);
        });
    }
    /**
     *  Every time the user enters something in the input field, the state gets updated.
     * @param key (the key of the state for identifying the field that needs to be updated)
     * @param value (the value that gets assigned to the identified state key)
     */
    handleInputChange(key, value) {
        // Example: if the key is username, this statement is the equivalent to the following one:
        // this.setState({'username': value});
        this.setState({ [key]: value });
    }


    componentWillMount() {
        var id = localStorage.getItem("selectedID");
        fetch(`${getDomain()}/users/`+id, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            return response.json() })
            .then( returnedUser => {
                this.setState({"user": new User(returnedUser)});
            })
            .catch(err => {
                console.log(err);
                alert("Something went wrong fetching the user info: " + err);
            });
    }
    /**
     * componentDidMount() is invoked immediately after a component is mounted (inserted into the tree).
     * Initialization that requires DOM nodes should go here.
     * If you need to load data from a remote endpoint, this is a good place to instantiate the network request.
     * You may call setState() immediately in componentDidMount().
     * It will trigger an extra rendering, but it will happen before the browser updates the screen.
     */

    render() {

        return (
            <BaseContainer>
                <FormContainer>
                    <Form>
                        <Label>Username</Label>
                        <InputField
                            placeholder={this.state.user.username}
                            onChange={e => {
                                this.handleInputChange("username", e.target.value);
                            }}
                        />

                        <Label>Birthday</Label>
                        <InputField
                            placeholder={this.state.user.birthday}
                            onChange={e => {
                                this.handleInputChange("birthday", e.target.value);
                            }}
                        />

                        <Label>ID</Label>
                        {this.state.user.id}

                        <Label>Creation Date</Label>
                        {this.state.user.creationDate}

                        <Label>Status</Label>
                        {this.state.user.status}

                    </Form>
                    <ButtonContainer>
                        <Button
                            width="50%"
                            onClick={() => {
                                this.cancelChanges();
                            }}
                        >
                            Cancel
                        </Button>
                        <Button
                            disabled={!this.state.username || !this.state.birthday}
                            width="50%"
                            onClick={() => {
                                this.save();
                            }}
                        >
                            Save
                        </Button>
                    </ButtonContainer>
                </FormContainer>
            </BaseContainer>
        );

    }
}

/**
 * You can get access to the history object's properties via the withRouter.
 * withRouter will pass updated match, location, and history props to the wrapped component whenever it renders.
 */
export default withRouter(EditProfile);