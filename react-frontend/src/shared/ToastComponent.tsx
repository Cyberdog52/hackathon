import { toast, ToastOptions } from 'react-toastify';

const defaultToastOptions: ToastOptions = {
    position: 'top-center',
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: 'colored',
};

export function presentErrorToast(message: string): void {
    toast.error(message, {
        ...defaultToastOptions,
        style: { background: 'var(--tertiary)' },
    });
}

export function presentInfoToast(message: string): void {
    toast.info(message, {
        ...defaultToastOptions,
        style: { background: 'var(--quarternary)' },
    });
}

export function presentSuccessToast(message: string): void {
    toast.success(message, {
        ...defaultToastOptions,
        style: { background: 'var(--secondary)' },
    });
}
