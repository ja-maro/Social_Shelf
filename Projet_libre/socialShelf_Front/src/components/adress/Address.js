import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AddressService from "../../services/address.service";

const Address = () => {
    const [address, setAddress] = useState({});
    const { id } = useParams();
    const navigate = useNavigate();

    const handleSubmit = () => {
        console.log("OK");
        AddressService.update(
            address.id,
            address.street,
            address.postalCode,
            address.city,
            address.country
        );
        navigate("/");
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

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAddress((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log();
    };

    const deleteAddress = () => {
        AddressService.deleteAddress(address.id).then((response) => {
            if (response.status === 200) {
                console.log(response);
                navigate("/");
            } else {
                console.log(response);
            }
        });
    };

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
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Postal Code :
                    <input
                        type="text"
                        name="postalCode"
                        placeholder="12345"
                        defaultValue={address.postalCode}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    City :
                    <input
                        type="text"
                        name="city"
                        placeholder="12345"
                        defaultValue={address.city}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Country :
                    <input
                        type="text"
                        name="country"
                        placeholder="12345"
                        defaultValue={address.country}
                        onChange={handleChange}
                    />
                </label>
                <input type="submit" value="Modify" />
            </form>
            <button onClick={deleteAddress}>Delete</button>
        </div>
    );

    return form;
};

export default Address;
