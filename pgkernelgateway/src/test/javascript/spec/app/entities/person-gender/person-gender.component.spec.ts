/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonGenderComponent } from 'app/entities/person-gender/person-gender.component';
import { PersonGenderService } from 'app/entities/person-gender/person-gender.service';
import { PersonGender } from 'app/shared/model/person-gender.model';

describe('Component Tests', () => {
  describe('PersonGender Management Component', () => {
    let comp: PersonGenderComponent;
    let fixture: ComponentFixture<PersonGenderComponent>;
    let service: PersonGenderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonGenderComponent],
        providers: []
      })
        .overrideTemplate(PersonGenderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonGenderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonGenderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonGender(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personGenders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
