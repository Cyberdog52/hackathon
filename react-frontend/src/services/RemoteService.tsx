import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import 'react-toastify/dist/ReactToastify.css';
import { presentErrorToast } from '../shared/ToastComponent';

class RemoteService {
    private instance: AxiosInstance;

    constructor() {
        this.instance = axios.create({
            baseURL: '/api',
            timeout: 5000,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
            },
        });
    }

    private handleResponse<T>(response: AxiosResponse<T>): T {
        return response.data;
    }

    private handleError<T>(error: AxiosError<T>): T {
        presentErrorToast(`Http error occurred with the message: ${error.message}`);
        throw new AxiosError(`Http error occurred with the message: ${error.message}!`);
    }

    get<T>(path: string, config?: AxiosRequestConfig): Promise<T> {
        return this.instance
            .get<T>(path, config)
            .then((response) => this.handleResponse<T>(response))
            .catch((error) => this.handleError(error));
    }

    post<T>(path: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return this.instance
            .post<T>(path, data, config)
            .then((response) => this.handleResponse<T>(response))
            .catch((error) => this.handleError(error));
    }

    put<T>(path: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
        return this.instance
            .put<T>(path, data, config)
            .then((response) => this.handleResponse<T>(response))
            .catch((error) => this.handleError(error));
    }

    delete<T>(path: string, config?: AxiosRequestConfig): Promise<T> {
        return this.instance
            .delete<T>(path, config)
            .then((response) => this.handleResponse<T>(response))
            .catch((error) => this.handleError(error));
    }
}

const remoteService = new RemoteService();

export default remoteService;
