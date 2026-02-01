import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import com.kms.katalon.core.configuration.RunConfiguration

def response = WS.sendRequest(findTestObject('Get Reqres'))

def json = new JsonSlurper().parseText(response.getResponseBodyContent())


def project = RunConfiguration.getProjectDir()
def csvFilePath = project + "/users.csv"  // simpan di root project


def header = "First Name,Last Name,Email\n"


Files.write(Paths.get(csvFilePath), header.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)


json.data.each { user ->
    def line = "${user.first_name},${user.last_name},${user.email}\n"
    Files.write(Paths.get(csvFilePath), line.getBytes(), StandardOpenOption.APPEND)
}

println "CSV generated successfully at: ${csvFilePath}"
