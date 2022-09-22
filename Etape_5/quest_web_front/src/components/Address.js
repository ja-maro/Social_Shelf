import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AddressService from "../services/address.service";

const Address = () => {
    const [address, setAddress] = useState({});
    const { id } = useParams();

    const handleSubmit = () => {
        console.log("OK");
    };

    useEffect(() => {
        if (!address.street) {
            AddressService.getById(id).then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setAddress(response.data);
                } else {
                    console.log(response);
                }
            });
        }
    });

    const form = (
        <div className="update">
            <form onSubmit={handleSubmit}>
                <label>
                    Street :
                    <input
                        type="text"
                        name="street"
                        placeholder="12 rue du porc"
                        defaultValue={address.street}
                        onChange={(event) => "Hello World"}
                    />
                </label>
                <label>
                    Postal Code :
                    <input
                        type="text"
                        name="Postal Code"
                        placeholder="12345"
                        defaultValue={address.postalCode}
                        onChange={(event) => "Hello World"}
                    />
                </label>
                <label>
                    City :
                    <input
                        type="text"
                        name="Postal Code"
                        placeholder="12345"
                        defaultValue={address.city}
                        onChange={(event) => "Hello World"}
                    />
                </label>
                <label>
                    Country :
                    <input
                        type="text"
                        name="Postal Code"
                        placeholder="12345"
                        defaultValue={address.country}
                        onChange={(event) => "Hello World"}
                    />
                </label>
                <input type="submit" value="Modify" />
            </form>
        </div>
    );

    return form;
};

export default Address;
