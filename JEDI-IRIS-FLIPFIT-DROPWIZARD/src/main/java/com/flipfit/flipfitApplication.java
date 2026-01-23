package com.flipfit;

import com.flipfit.health.PingHealthCheck;
import com.flipfit.resources.RootResource;
import com.flipfit.resources.UserResource;
import com.flipfit.resources.AdminResource;
import com.flipfit.resources.GymMemberResource;
import com.flipfit.resources.GymOwnerResource;
import com.flipfit.resources.CenterResource;
import com.flipfit.resources.SlotResource;
import com.flipfit.resources.BookingResource;
import com.flipfit.resources.PaymentResource;

import com.flipfit.db.UserDAO;
import com.flipfit.db.CenterDAO;
import com.flipfit.db.SlotDAO;
import com.flipfit.db.BookingDAO;
import com.flipfit.db.WaitListDAO;
import com.flipfit.db.PaymentDAO;

import com.flipfit.db.mapper.RoleArgumentFactory;
import com.flipfit.db.mapper.RoleColumnMapper;
import com.flipfit.db.mapper.StatusArgumentFactory;
import com.flipfit.db.mapper.StatusColumnMapper;
import com.flipfit.db.mapper.LocalDateTimeArgumentFactory;
import com.flipfit.db.mapper.LocalDateTimeMapper;

import com.flipfit.core.User;
import com.flipfit.core.Admin;
import com.flipfit.core.GymMember;
import com.flipfit.core.GymOwner;
import com.flipfit.core.Center;
import com.flipfit.core.Slot;
import com.flipfit.core.Booking;
import com.flipfit.core.WaitList;
import com.flipfit.core.Payment;

import com.flipfit.service.UserService;
import com.flipfit.service.CenterService;
import com.flipfit.service.SlotService;
import com.flipfit.service.BookingService;
import com.flipfit.service.WaitListService;
import com.flipfit.service.PaymentService;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

public class flipfitApplication extends Application<flipfitConfiguration> {

    public static void main(final String[] args) throws Exception {
        new flipfitApplication().run(args);
    }

    @Override
    public String getName() {
        return "flipfit";
    }

    @Override
    public void initialize(final Bootstrap<flipfitConfiguration> bootstrap) {

    }

    @Override
    public void run(final flipfitConfiguration configuration,
                    final Environment environment) {
        // Initialize JDBI
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

        // Register custom mappers and argument factories
        jdbi.registerArgument(new RoleArgumentFactory());
        jdbi.registerColumnMapper(new RoleColumnMapper());
        jdbi.registerArgument(new StatusArgumentFactory());
        jdbi.registerColumnMapper(new StatusColumnMapper());
        jdbi.registerArgument(new LocalDateTimeArgumentFactory());
        jdbi.registerColumnMapper(new LocalDateTimeMapper());


        // Register BeanMappers for all core classes
        jdbi.registerRowMapper(BeanMapper.factory(User.class));
        jdbi.registerRowMapper(BeanMapper.factory(Admin.class));
        jdbi.registerRowMapper(BeanMapper.factory(GymMember.class));
        jdbi.registerRowMapper(BeanMapper.factory(GymOwner.class));
        jdbi.registerRowMapper(BeanMapper.factory(Center.class));
        jdbi.registerRowMapper(BeanMapper.factory(Slot.class));
        jdbi.registerRowMapper(BeanMapper.factory(Booking.class));
        jdbi.registerRowMapper(BeanMapper.factory(WaitList.class));
        jdbi.registerRowMapper(BeanMapper.factory(Payment.class));


        // Initialize DAOs
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        userDAO.createTable(); // Create users table on startup

        final CenterDAO centerDAO = jdbi.onDemand(CenterDAO.class);
        centerDAO.createTable();

        final SlotDAO slotDAO = jdbi.onDemand(SlotDAO.class);
        slotDAO.createTable();

        final BookingDAO bookingDAO = jdbi.onDemand(BookingDAO.class);
        bookingDAO.createTable();

        final WaitListDAO waitListDAO = jdbi.onDemand(WaitListDAO.class);
        waitListDAO.createTable();

        final PaymentDAO paymentDAO = jdbi.onDemand(PaymentDAO.class);
        paymentDAO.createTable();

        // Initialize Services
        final UserService userService = new UserService(userDAO);
        final CenterService centerService = new CenterService(centerDAO);
        final SlotService slotService = new SlotService(slotDAO);
        final BookingService bookingService = new BookingService(bookingDAO, slotService);
        final WaitListService waitListService = new WaitListService(waitListDAO, slotDAO, bookingService);
        final PaymentService paymentService = new PaymentService(paymentDAO, bookingDAO);


        // Register health checks
        environment.healthChecks().register("ping", new PingHealthCheck());

        // Register resources
        environment.jersey().register(new RootResource());
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new AdminResource(centerService, userService));
        environment.jersey().register(new GymMemberResource(centerService, slotService, bookingService, waitListService));
        environment.jersey().register(new GymOwnerResource(centerService, slotService));
        environment.jersey().register(new CenterResource(centerService));
        environment.jersey().register(new SlotResource(slotService));
        environment.jersey().register(new BookingResource(bookingService));
        environment.jersey().register(new PaymentResource(paymentService));
    }
}
