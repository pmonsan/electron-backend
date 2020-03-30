/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageComponent } from 'app/entities/pg-message/pg-message.component';
import { PgMessageService } from 'app/entities/pg-message/pg-message.service';
import { PgMessage } from 'app/shared/model/pg-message.model';

describe('Component Tests', () => {
  describe('PgMessage Management Component', () => {
    let comp: PgMessageComponent;
    let fixture: ComponentFixture<PgMessageComponent>;
    let service: PgMessageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageComponent],
        providers: []
      })
        .overrideTemplate(PgMessageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgMessage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgMessages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
