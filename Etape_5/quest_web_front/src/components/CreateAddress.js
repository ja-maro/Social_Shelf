import "../styles/Login.scss";
import { useState } from "react";
import { Alert } from "@mui/material";
import AddressService from "../services/address.service";

function CreateAddress() {
    const [street, setStreet] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [city, setCity] = useState("");
    const [country, setCountry] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const [authMessage, setAuthMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        await AddressService.create(street, postalCode, city, country).then(
            (res) => (response = res)
        );
        if (response.status === 201) {
            setIsAlert(true);
            setAuthMessage({
                severity: "success",
                message: "Created",
                status: "201",
            });
        } else if (response.status === 409) {
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Address already exists",
                status: "409",
            });
        } else {
            setIsAlert(true);
            setAuthMessage({
                severity: "error",
                message: "Error",
                status: "",
            });
        }
    };

    const form = (
        <form onSubmit={handleSubmit}>
            <h1 className="title"> Create new personal address </h1>
            <label>
                Street :
                <input
                    type="text"
                    name="street"
                    placeholder="12 rue du Bac"
                    onChange={(event) => setStreet(event.target.value)}
                />
            </label>
            <label>
                Postal Code :
                <input
                    type="text"
                    name="postalCode"
                    placeholder="95230"
                    onChange={(event) => setPostalCode(event.target.value)}
                />
            </label>
            <label>
                City :
                <input
                    type="text"
                    name="city"
                    placeholder="Vernouillet"
                    onChange={(event) => setCity(event.target.value)}
                />
            </label>
            <label>
                Country :
                <input
                    type="text"
                    name="country"
                    placeholder="France"
                    onChange={(event) => setCountry(event.target.value)}
                />
            </label>
            <input type="submit" value="Send" />
        </form>
    );

    if (!isAlert) {
        return <div className="login">{form}</div>;
    } else {
        return (
            <div className="login">
                {form}
                <Alert
                    className="alert"
                    variant="filled"
                    severity={authMessage.severity}
                >
                    {authMessage.message}
                </Alert>
            </div>
        );
    }
}
export default CreateAddress;
