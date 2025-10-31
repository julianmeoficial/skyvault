import React, { useState, useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import { BackButton } from '../../shared/components/ui/BackButton/BackButton';
import { CrystalSlider } from '../../shared/components/ui/CrystalSlider/CrystalSlider';
import Footer from '../../shared/components/layout/Footer/Footer';
import styles from './Contact.module.css';

interface FormData {
    fullName: string;
    email: string;
    category: string;
    message: string;
}

interface FormErrors {
    fullName?: string;
    email?: string;
    category?: string;
    message?: string;
}

const Contact: React.FC = () => {
    const [selectedCategory, setSelectedCategory] = useState<string>('');
    const [showSuccess, setShowSuccess] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [isMobile, setIsMobile] = useState(window.innerWidth <= 1024);

    const [formData, setFormData] = useState<FormData>({
        fullName: '',
        email: '',
        category: '',
        message: ''
    });

    const [errors, setErrors] = useState<FormErrors>({});

    const formRef = useRef<HTMLFormElement>(null);
    const headerRef = useRef<HTMLDivElement>(null);
    const sidebarRef = useRef<HTMLDivElement>(null);
    const introRef = useRef<HTMLDivElement>(null);
    const successModalRef = useRef<HTMLDivElement>(null);

    const categories = [
        {
            id: 'soporte',
            name: 'Soporte técnico',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M11.42 15.17L17.25 21A2.652 2.652 0 0021 17.25l-5.877-5.877M11.42 15.17l2.496-3.03c.317-.384.74-.626 1.208-.766M11.42 15.17l-4.655 5.653a2.548 2.548 0 11-3.586-3.586l6.837-5.63m5.108-.233c.55-.164 1.163-.188 1.743-.14a4.5 4.5 0 004.486-6.336l-3.276 3.277a3.004 3.004 0 01-2.25-2.25l3.276-3.276a4.5 4.5 0 00-6.336 4.486c.091 1.076-.071 2.264-.904 2.95l-.102.085m-1.745 1.437L5.909 7.5H4.5L2.25 3.75l1.5-1.5L7.5 4.5v1.409l4.26 4.26m-1.745 1.437l1.745-1.437m6.615 8.206L15.75 15.75M4.867 19.125h.008v.008h-.008v-.008z"/>
                </svg>
            )
        },
        {
            id: 'consultas',
            name: 'Consultas',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9 5.25h.008v.008H12v-.008z"/>
                </svg>
            )
        },
        {
            id: 'sugerencias',
            name: 'Sugerencias',
            icon: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M12 18v-5.25m0 0a6.01 6.01 0 001.5-.189m-1.5.189a6.01 6.01 0 01-1.5-.189m3.75 7.478a12.06 12.06 0 01-4.5 0m3.75 2.383a14.406 14.406 0 01-3 0M14.25 18v-.192c0-.983.658-1.823 1.508-2.316a7.5 7.5 0 10-7.517 0c.85.493 1.509 1.333 1.509 2.316V18"/>
                </svg>
            )
        }
    ];

    useEffect(() => {
        window.scrollTo(0, 0);

        const handleResize = () => {
            setIsMobile(window.innerWidth <= 1024);
        };

        window.addEventListener('resize', handleResize);

        // Animaciones GSAP suaves sin opacidad
        const tl = gsap.timeline();

        if (headerRef.current) {
            gsap.set(headerRef.current, { opacity: 1 });
            tl.from(headerRef.current, {
                y: -30,
                duration: 0.6,
                ease: 'power2.out'
            });
        }

        if (introRef.current) {
            gsap.set(introRef.current, { opacity: 1 });
            tl.from(introRef.current, {
                y: 20,
                duration: 0.5,
                ease: 'power2.out'
            }, '-=0.3');
        }

        if (sidebarRef.current) {
            gsap.set(sidebarRef.current, { opacity: 1 });
            tl.from(sidebarRef.current, {
                x: -30,
                duration: 0.5,
                ease: 'power2.out'
            }, '-=0.3');
        }

        if (formRef.current) {
            gsap.set(formRef.current, { opacity: 1 });
            tl.from(formRef.current, {
                scale: 0.98,
                duration: 0.5,
                ease: 'power2.out'
            }, '-=0.2');
        }

        return () => window.removeEventListener('resize', handleResize);
    }, []);

    const validateEmail = (email: string): boolean => {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    };

    const validateForm = (): boolean => {
        const newErrors: FormErrors = {};

        if (!formData.fullName.trim()) {
            newErrors.fullName = 'El nombre completo es obligatorio';
        }

        if (!formData.email.trim()) {
            newErrors.email = 'El correo electrónico es obligatorio';
        } else if (!validateEmail(formData.email)) {
            newErrors.email = 'El correo electrónico no es válido';
        }

        if (!formData.category) {
            newErrors.category = 'Debes seleccionar una categoría';
        }

        if (!formData.message.trim()) {
            newErrors.message = 'El mensaje es obligatorio';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const {name, value} = e.target;
        setFormData(prev => ({...prev, [name]: value}));

        // Limpiar error al escribir
        if (errors[name as keyof FormErrors]) {
            setErrors(prev => ({...prev, [name]: undefined}));
        }
    };

    const handleCategoryClick = (categoryId: string) => {
        setSelectedCategory(categoryId);
        setFormData(prev => ({...prev, category: categoryId}));
        if (errors.category) {
            setErrors(prev => ({...prev, category: undefined}));
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validateForm()) {
            // Shake animation en errores
            if (formRef.current) {
                gsap.to(formRef.current, {
                    keyframes: [
                        {x: -10, duration: 0.1},
                        {x: 10, duration: 0.1},
                        {x: -10, duration: 0.1},
                        {x: 10, duration: 0.1},
                        {x: 0, duration: 0.1}
                    ],
                    ease: 'power2.inOut'
                });
            }
            return;
        }

        setIsSubmitting(true);

        // Simular envío (2 segundos)
        setTimeout(() => {
            setIsSubmitting(false);
            setShowSuccess(true);

            // Animar modal de éxito
            if (successModalRef.current) {
                gsap.fromTo(
                    successModalRef.current,
                    {scale: 0, opacity: 0},
                    {scale: 1, opacity: 1, duration: 0.5, ease: 'back.out(1.7)'}
                );
            }

            // Reset form
            setFormData({
                fullName: '',
                email: '',
                category: '',
                message: ''
            });
            setSelectedCategory('');
        }, 2000);
    };

    const closeSuccessModal = () => {
        if (successModalRef.current) {
            gsap.to(successModalRef.current, {
                scale: 0,
                opacity: 0,
                duration: 0.3,
                ease: 'back.in(1.7)',
                onComplete: () => setShowSuccess(false)
            });
        }
    };

    const SidebarComponent = () => (
        <aside className={styles.sidebar} ref={isMobile ? null : sidebarRef}>
            <div className={styles.sidebarCard}>
                <h2 className={styles.sidebarTitle}>Opciones de contacto</h2>
                <nav className={styles.categoryNav}>
                    {categories.map((category) => (
                        <button
                            key={category.id}
                            className={`${styles.categoryButton} ${selectedCategory === category.id ? styles.active : ''}`}
                            onClick={() => handleCategoryClick(category.id)}
                            type="button"
                        >
                            <span className={styles.categoryIconWrapper}>{category.icon}</span>
                            <span className={styles.categoryName}>{category.name}</span>
                        </button>
                    ))}
                </nav>
            </div>
        </aside>
    );

    return (
        <div className={styles.contactPage}>
            <div className={styles.topControls}>
                <BackButton to="/" label="Volver al Inicio" />
                <CrystalSlider position="top-right" />
            </div>

            <div className={styles.container}>
                <header className={styles.header} ref={headerRef}>
                    <div className={styles.headerIcon}>
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75" />
                        </svg>
                    </div>
                    <h1 className={styles.title}>Contáctanos</h1>
                    <p className={styles.subtitle}>Estamos aquí para ayudarte con cualquier consulta</p>
                </header>

                <div className={styles.introCard} ref={introRef}>
                    <p className={styles.introText}>
                        Estamos aquí para ayudarte. Si tienes alguna pregunta, necesitas soporte técnico o deseas enviarnos tus sugerencias para mejorar nuestra plataforma, no dudes en contactarnos. Completa el formulario a continuación con tu nombre, correo electrónico, selecciona la categoría que mejor describa tu mensaje y escribe tu consulta o comentario. Nuestro equipo revisará tu mensaje y te responderá lo antes posible.
                    </p>
                </div>

                {isMobile ? (
                    <div className={styles.content}>
                        <SidebarComponent />
                        <form className={styles.form} onSubmit={handleSubmit} ref={formRef}>
                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="fullName">
                                    Nombre completo *
                                </label>
                                <input
                                    className={`${styles.formInput} ${errors.fullName ? styles.inputError : ''}`}
                                    type="text"
                                    id="fullName"
                                    name="fullName"
                                    value={formData.fullName}
                                    onChange={handleInputChange}
                                    placeholder="Escribe tu nombre completo"
                                />
                                {errors.fullName && <span className={styles.errorText}>{errors.fullName}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="email">
                                    Correo electrónico *
                                </label>
                                <input
                                    className={`${styles.formInput} ${errors.email ? styles.inputError : ''}`}
                                    type="email"
                                    id="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleInputChange}
                                    placeholder="correo@ejemplo.com"
                                />
                                {errors.email && <span className={styles.errorText}>{errors.email}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="category">
                                    Categoría *
                                </label>
                                <select
                                    className={`${styles.formSelect} ${errors.category ? styles.inputError : ''}`}
                                    id="category"
                                    name="category"
                                    value={formData.category}
                                    onChange={handleInputChange}
                                >
                                    <option value="">Selecciona una categoría</option>
                                    <option value="soporte">Soporte técnico</option>
                                    <option value="consultas">Consultas</option>
                                    <option value="sugerencias">Sugerencias</option>
                                </select>
                                {errors.category && <span className={styles.errorText}>{errors.category}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="message">
                                    Mensaje *
                                </label>
                                <textarea
                                    className={`${styles.formTextarea} ${errors.message ? styles.inputError : ''}`}
                                    id="message"
                                    name="message"
                                    value={formData.message}
                                    onChange={handleInputChange}
                                    placeholder="Escribe tu consulta o comentario aquí..."
                                    rows={6}
                                />
                                {errors.message && <span className={styles.errorText}>{errors.message}</span>}
                            </div>

                            <button
                                className={styles.submitButton}
                                type="submit"
                                disabled={isSubmitting}
                            >
                                {isSubmitting ? (
                                    <>
                                        <span className={styles.spinner} />
                                        Enviando...
                                    </>
                                ) : (
                                    'Enviar mensaje'
                                )}
                            </button>
                        </form>
                    </div>
                ) : (
                    <div className={styles.content}>
                        <SidebarComponent />
                        <form className={styles.form} onSubmit={handleSubmit} ref={formRef}>
                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="fullName">
                                    Nombre completo *
                                </label>
                                <input
                                    className={`${styles.formInput} ${errors.fullName ? styles.inputError : ''}`}
                                    type="text"
                                    id="fullName"
                                    name="fullName"
                                    value={formData.fullName}
                                    onChange={handleInputChange}
                                    placeholder="Escribe tu nombre completo"
                                />
                                {errors.fullName && <span className={styles.errorText}>{errors.fullName}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="email">
                                    Correo electrónico *
                                </label>
                                <input
                                    className={`${styles.formInput} ${errors.email ? styles.inputError : ''}`}
                                    type="email"
                                    id="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleInputChange}
                                    placeholder="correo@ejemplo.com"
                                />
                                {errors.email && <span className={styles.errorText}>{errors.email}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="category">
                                    Categoría *
                                </label>
                                <select
                                    className={`${styles.formSelect} ${errors.category ? styles.inputError : ''}`}
                                    id="category"
                                    name="category"
                                    value={formData.category}
                                    onChange={handleInputChange}
                                >
                                    <option value="">Selecciona una categoría</option>
                                    <option value="soporte">Soporte técnico</option>
                                    <option value="consultas">Consultas</option>
                                    <option value="sugerencias">Sugerencias</option>
                                </select>
                                {errors.category && <span className={styles.errorText}>{errors.category}</span>}
                            </div>

                            <div className={styles.formGroup}>
                                <label className={styles.formLabel} htmlFor="message">
                                    Mensaje *
                                </label>
                                <textarea
                                    className={`${styles.formTextarea} ${errors.message ? styles.inputError : ''}`}
                                    id="message"
                                    name="message"
                                    value={formData.message}
                                    onChange={handleInputChange}
                                    placeholder="Escribe tu consulta o comentario aquí..."
                                    rows={6}
                                />
                                {errors.message && <span className={styles.errorText}>{errors.message}</span>}
                            </div>

                            <button
                                className={styles.submitButton}
                                type="submit"
                                disabled={isSubmitting}
                            >
                                {isSubmitting ? (
                                    <>
                                        <span className={styles.spinner} />
                                        Enviando...
                                    </>
                                ) : (
                                    'Enviar mensaje'
                                )}
                            </button>
                        </form>
                    </div>
                )}
            </div>

            {showSuccess && (
                <div className={styles.successOverlay} onClick={closeSuccessModal}>
                    <div
                        className={styles.successModal}
                        ref={successModalRef}
                        onClick={(e) => e.stopPropagation()}
                    >
                        <button className={styles.closeButton} onClick={closeSuccessModal}>
                            ×
                        </button>
                        <div className={styles.successHeader}>
                            <div className={styles.successIconWrapper}>
                                <div className={styles.successIcon}>
                                    <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M20 7L9.00004 18L3.99994 13"
                                            stroke="currentColor"
                                            strokeWidth="2"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                        />
                                    </svg>
                                </div>
                            </div>
                            <div className={styles.successContent}>
                                <h3 className={styles.successTitle}>¡Mensaje enviado con éxito!</h3>
                                <p className={styles.successMessage}>
                                    Gracias por contactarnos. Tu mensaje ha sido recibido y nuestro equipo lo revisará pronto. Te responderemos a tu correo electrónico lo antes posible.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            <Footer />
        </div>
    );
};

export default Contact;
