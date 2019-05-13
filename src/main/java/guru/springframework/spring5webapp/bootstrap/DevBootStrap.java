package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent>
{
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootStrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository)
    {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        initData();
    }

    private void initData()
    {
        initAndSaveAuthorAndBook("Eric", "Evans", "Domain Driven Design", "1234", "Harper Collins", "Harper Collins road");
        initAndSaveAuthorAndBook("Rob", "Johnson", "J2EE Development Without EJB", "23444", "Worx", "Worx Work Place Road");
    }

    private void initAndSaveAuthorAndBook(String firstName, String lastName, String title, String isbn, String publisherName, String publisherAddress)
    {
        Author author = new Author(firstName, lastName);
        Publisher publisher = new Publisher(publisherName, publisherAddress);
        Book book = new Book(title, isbn, publisher);

        author.getBooks().add(book);
        book.getAuthors().add(author);

        authorRepository.save(author);
        publisherRepository.save(publisher); //order of saves is important!
        bookRepository.save(book);
    }
}
