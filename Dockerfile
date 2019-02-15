FROM openjdk:8-jre

# Set the working directory
RUN mkdir -p /opt/app/
WORKDIR /opt/app/

# Install unzip to extract the application archive
RUN apt-get update && apt-get upgrade -y
RUN apt-get install unzip -y

# Define variables
ARG BUILD_DIR=com.avojak.webapp.p2.inspector.packaging/target
ARG PRODUCT_ID=com.avojak.webapp.p2.inspector.product
ARG TARGET_PLATFORM=linux.gtk.x86_64

# Copy and unzip the Linux executable
COPY ${BUILD_DIR}/products/${PRODUCT_ID}-${TARGET_PLATFORM}.zip .
RUN unzip ${PRODUCT_ID}-${TARGET_PLATFORM}.zip && rm ${PRODUCT_ID}-${TARGET_PLATFORM}.zip

# Set the command to run the executable
CMD ["./p2-inspector"]
