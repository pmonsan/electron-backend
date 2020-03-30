/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonTypeComponent } from 'app/entities/person-type/person-type.component';
import { PersonTypeService } from 'app/entities/person-type/person-type.service';
import { PersonType } from 'app/shared/model/person-type.model';

describe('Component Tests', () => {
  describe('PersonType Management Component', () => {
    let comp: PersonTypeComponent;
    let fixture: ComponentFixture<PersonTypeComponent>;
    let service: PersonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonTypeComponent],
        providers: []
      })
        .overrideTemplate(PersonTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PersonType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.personTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
